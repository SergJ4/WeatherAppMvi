package com.example.weather

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.SchedulersProvider
import com.example.core.di.scopes.FragmentScope
import com.example.core.models.City
import com.example.core.usecase.RefreshWeatherRepo
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ListFragmentFeature @Inject constructor(refreshWeatherRepo: RefreshWeatherRepo) :
    ActorReducerFeature<
            ListFragmentFeature.Wish,
            ListFragmentFeature.Effect,
            ListFragmentFeature.State,
            ListFragmentFeature.News
            >(
        initialState = State(listOf(), true),
        actor = ActorImpl(refreshWeatherRepo),
        reducer = ReducerImpl()
    ) {

    sealed class Wish {
        object ShowCitiesList : Wish()
        object Refresh : Wish()
    }

    sealed class Effect {
        data class Refresh(val isStarted: Boolean) : Effect()
    }

    data class State(
        val list: List<City>,
        val isLoading: Boolean
    )

    sealed class News {

    }

    class ActorImpl(private val refreshWeatherRepo: RefreshWeatherRepo) :
        Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> =
            when (wish) {
                Wish.ShowCitiesList -> TODO()

                Wish.Refresh -> Observable
                    .just(Effect.Refresh(true))
                    .flatMapCompletable { refreshWeatherRepo() }
                    .andThen { Observable.just(Effect.Refresh(false)) }
                    .subscribeOn(SchedulersProvider.io())
                    .observeOn(SchedulersProvider.ui())
                    .toObservable()
            }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State =
            when (effect) {
                is Effect.Refresh -> {
                    state.copy(list = state.list, isLoading = effect.isStarted)
                }
            }
    }
}