package com.example.weather

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.SchedulersProvider
import com.example.core.di.scopes.FragmentScope
import com.example.core.models.City
import com.example.core.models.SearchCity
import com.example.core.usecase.RefreshWeatherRepo
import com.example.weather.usecase.AddCity
import com.example.weather.usecase.FetchCitiesWithWeather
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ListFragmentFeature @Inject constructor(
    refreshWeatherRepo: RefreshWeatherRepo,
    fetchCitiesWithWeather: FetchCitiesWithWeather,
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
            addCity
        ),
        reducer = ReducerImpl(),
        newsPublisher = NewsPublisherImpl()
    ) {

    sealed class Wish {
        object Refresh : Wish()
        data class CityChosen(val city: SearchCity) : Wish()
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
        private val addCity: AddCity
    ) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> =
            when (wish) {

                Wish.Refresh -> refreshWeatherRepo()
                    .andThen(fetchCitiesWithWeather())
                    .map {
                        Effect.DataLoaded(it) as Effect
                    }
                    .toObservable()
                    .subscribeOn(SchedulersProvider.io())
                    .observeOn(SchedulersProvider.ui())
                    .startWith(Observable.just(Effect.Loading))

                is Wish.CityChosen -> addCity(wish.city)
                    .subscribeOn(SchedulersProvider.io())
                    .observeOn(SchedulersProvider.ui())
                    .map { Effect.CityAdded as Effect }
                    .startWith(Effect.Loading)
                    .onErrorReturn { Effect.ErrorChoosingCity }
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