package com.example.weather

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.SchedulersProvider
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.DataWrapper
import com.example.core.models.City
import com.example.core.usecase.RefreshWeatherRepo
import com.example.weather.usecase.AddCity
import com.example.weather.usecase.FetchCitiesWithWeather
import com.example.weather.usecase.GooglePlaces
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Provider

@FragmentScope
class ListFragmentFeature @Inject constructor(
    refreshWeatherRepo: RefreshWeatherRepo,
    fetchCitiesWithWeather: FetchCitiesWithWeather,
    googlePlacesProvider: Provider<GooglePlaces>,
    addCity: AddCity
) :
    ActorReducerFeature<
            ListFragmentFeature.Wish,
            ListFragmentFeature.Effect,
            ListFragmentFeature.State,
            ListFragmentFeature.News
            >(
        initialState = State(listOf(), true),
        actor = ActorImpl(
            refreshWeatherRepo,
            fetchCitiesWithWeather,
            googlePlacesProvider,
            addCity
        ),
        reducer = ReducerImpl(),
        newsPublisher = NewsPublisherImpl()
    ) {

    sealed class Wish {
        object ShowCitiesList : Wish()
        object Refresh : Wish()
        data class ActivityResult(
            val requestCode: Int,
            val resultCode: Int,
            val data: DataWrapper
        ) : Wish()
    }

    sealed class Effect {
        object Loading : Effect()
        data class DataLoaded(val cities: List<City>) : Effect()
        object ErrorChoosingCity : Effect()
        object CityAdded : Effect()

    }

    data class State(
        val list: List<City>,
        val isLoading: Boolean
    )

    sealed class News {
        object ErrorChoosingCity : News()
    }

    class ActorImpl(
        private val refreshWeatherRepo: RefreshWeatherRepo,
        private val fetchCitiesWithWeather: FetchCitiesWithWeather,
        private val googlePlacesProvider: Provider<GooglePlaces>,
        private val addCity: AddCity
    ) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> =
            when (wish) {
                Wish.ShowCitiesList -> {
                    val googlePlaces = googlePlacesProvider.get()
                    Observable
                        .fromCallable { googlePlaces.showCitiesList() }
                        .flatMap { googlePlaces.selectedCity() }
                        .take(1)
                        .flatMap {
                            addCity(it)
                                .subscribeOn(SchedulersProvider.io())
                                .observeOn(SchedulersProvider.ui())
                                .map { Effect.CityAdded as Effect }
                                .startWith(Effect.Loading)
                                .onErrorReturn { Effect.ErrorChoosingCity }
                        }
                        .onErrorReturn { Effect.ErrorChoosingCity }
                }

                Wish.Refresh -> refreshWeatherRepo()
                    .andThen(fetchCitiesWithWeather())
                    .map {
                        Effect.DataLoaded(it) as Effect
                    }
                    .toObservable()
                    .subscribeOn(SchedulersProvider.io())
                    .observeOn(SchedulersProvider.ui())
                    .startWith(Observable.just(Effect.Loading))

                is Wish.ActivityResult -> Observable.fromCallable {
                    googlePlacesProvider
                        .get()
                        .onActivityResult(wish.requestCode, wish.resultCode, wish.data)
                }
                    .map { Effect.CityAdded }
            }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.Loading -> state.copy(list = state.list, isLoading = true)
                is Effect.DataLoaded -> state.copy(list = effect.cities, isLoading = false)
                Effect.ErrorChoosingCity -> state.copy(list = state.list, isLoading = false)
                Effect.CityAdded -> state.copy(list = state.list, isLoading = false)
            }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? {
            return if (effect == Effect.ErrorChoosingCity) {
                News.ErrorChoosingCity
            } else {
                null
            }
        }
    }
}