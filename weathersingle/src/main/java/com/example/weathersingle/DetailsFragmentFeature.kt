package com.example.weathersingle

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.SchedulersProvider
import com.example.core.di.scopes.FragmentScope
import com.example.core.exceptions.Error
import com.example.core.exceptions.NetworkConnectionError
import com.example.core.exceptions.RefreshDataError
import com.example.core.exceptions.ResourceNotFoundError
import com.example.core.interfaces.ApiErrors
import com.example.core.interfaces.Router
import com.example.core.models.City
import com.example.core.usecase.RefreshWeatherRepo
import com.example.weathersingle.usecase.FetchExactCityWeather
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

@FragmentScope
class DetailsFragmentFeature @Inject constructor(
    router: Router,
    fetchExactCityWeather: FetchExactCityWeather,
    refreshWeatherRepo: RefreshWeatherRepo,
    @Named(CITY_ID_ARG) cityId: Long,
    apiErrors: ApiErrors
) :
    ActorReducerFeature<
            DetailsFragmentFeature.Wish,
            DetailsFragmentFeature.Effect,
            DetailsFragmentFeature.State,
            DetailsFragmentFeature.News>(
        initialState = State(null, true),
        reducer = ReducerImpl(),
        actor = ActorImpl(router, fetchExactCityWeather, refreshWeatherRepo, cityId),
        bootstrapper = BootstrapperImpl(apiErrors),
        newsPublisher = NewsPublisherImpl()
    ) {

    sealed class Wish {
        object Refresh : Wish()
        object Back : Wish()
        object LoadCity : Wish()
        data class ApiError(val error: Error) : Wish()
    }

    sealed class Effect {
        data class Loading(val isLoading: Boolean) : Effect()
        data class CityLoaded(val city: City) : Effect()
    }

    data class State(val currentCity: City?, val isLoading: Boolean)

    sealed class News {
        object NetworkConnectionError : News()
        object CityNotFoundError : News()
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            is Effect.Loading -> state.copy(isLoading = effect.isLoading)
            is Effect.CityLoaded -> state.copy(currentCity = effect.city, isLoading = false)
        }
    }

    class ActorImpl(
        private val router: Router,
        private val fetchExactCityWeather: FetchExactCityWeather,
        private val refreshWeatherRepo: RefreshWeatherRepo,
        private val cityId: Long
    ) : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> = when (wish) {

            Wish.LoadCity -> fetchExactCityWeather(cityId)
                .toObservable()
                .map { Effect.CityLoaded(it) as Effect }
                .subscribeOn(SchedulersProvider.io())
                .observeOn(SchedulersProvider.ui())
                .startWith(Observable.just(Effect.Loading(true)))

            Wish.Refresh -> refreshWeatherRepo()
                .andThen(fetchExactCityWeather(cityId).toObservable())
                .map { Effect.CityLoaded(it) as Effect }
                .subscribeOn(SchedulersProvider.io())
                .observeOn(SchedulersProvider.ui())
                .startWith(Observable.just(Effect.Loading(true)))

            Wish.Back -> {
                router.back()
                Observable.just(Effect.Loading(false))
            }

            is Wish.ApiError -> Observable.just(Effect.Loading(false))
        }
    }

    class NewsPublisherImpl : NewsPublisher<Wish, Effect, State, News> {
        override fun invoke(wish: Wish, effect: Effect, state: State): News? = when (wish) {
            is Wish.ApiError -> mapApiError(wish.error)
            else -> null
        }

        private fun mapApiError(error: Error): News = when (error) {
            RefreshDataError, ResourceNotFoundError -> News.CityNotFoundError
            NetworkConnectionError -> News.NetworkConnectionError
        }
    }

    class BootstrapperImpl(private val apiErrors: ApiErrors) : Bootstrapper<Wish> {
        override fun invoke(): Observable<Wish> = apiErrors().map { Wish.ApiError(it) }
    }
}