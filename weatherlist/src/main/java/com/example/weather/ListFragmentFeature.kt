package com.example.weather

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.SchedulersProvider
import com.example.core.di.scopes.FragmentScope
import com.example.core.models.City
import com.example.core.usecase.RefreshWeatherRepo
import com.example.weather.usecase.FetchCitiesWithWeather
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ListFragmentFeature @Inject constructor(
    refreshWeatherRepo: RefreshWeatherRepo,
    fetchCitiesWithWeather: FetchCitiesWithWeather
) :
    ActorReducerFeature<
            ListFragmentFeature.Wish,
            ListFragmentFeature.Effect,
            ListFragmentFeature.State,
            ListFragmentFeature.News
            >(
        initialState = State(listOf(), true),
        actor = ActorImpl(refreshWeatherRepo, fetchCitiesWithWeather),
        reducer = ReducerImpl()
    ) {

    sealed class Wish {
        object ShowCitiesList : Wish()
        object Refresh : Wish()
    }

    sealed class Effect {
        object Refresh : Effect()
        data class DataLoaded(val cities: List<City>) : Effect()
    }

    data class State(
        val list: List<City>,
        val isLoading: Boolean
    )

    sealed class News

    class ActorImpl(
        private val refreshWeatherRepo: RefreshWeatherRepo,
        private val fetchCitiesWithWeather: FetchCitiesWithWeather
    ) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> =
            when (wish) {
                Wish.ShowCitiesList -> TODO()

                Wish.Refresh -> refreshWeatherRepo()
                    .andThen(fetchCitiesWithWeather())
                    .map {
                        Effect.DataLoaded(it) as Effect
                    }
                    .toObservable()
                    .subscribeOn(SchedulersProvider.io())
                    .observeOn(SchedulersProvider.ui())
                    .startWith(Observable.just(Effect.Refresh))
            }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.Refresh -> {
                    state.copy(list = state.list, isLoading = true)
                }
                is Effect.DataLoaded -> {
                    state.copy(list = effect.cities, isLoading = false)
                }
            }
    }
}