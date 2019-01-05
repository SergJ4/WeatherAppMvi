package com.example.weather

import androidx.lifecycle.ViewModel
import com.badoo.mvicore.extension.mapNotNull
import com.example.core.SchedulersProvider
import com.example.core.interfaces.*
import com.example.core.models.City
import com.example.weather.adapter.CityWeatherItem
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

class ListFragmentViewModel(
    listFeature: ListFragmentFeature,
    private val imageLoader: ImageLoader,
    private val router: Router,
    private val colors: Colors,
    private val strings: Strings
) : ViewModel() {

    private val outputMapper: (ListFragmentFeature.State) -> ListFragment.Model = {
        ListFragment.Model(createItems(it.list), it.isLoading)
    }

    private val newsMapper: (ListFragmentFeature.News) -> String = {
        if (it == ListFragmentFeature.News.ErrorChoosingCity) {
            strings.errorChoosingCityString()
        } else {
            strings.unknownErrorString()
        }
    }

    val input: Consumer<ListFragment.UiEvent> =
        Consumer { event ->
            when (event) {
                ListFragment.UiEvent.SwipeRefresh -> listFeature.accept(ListFragmentFeature.Wish.Refresh)
                ListFragment.UiEvent.AddCityClick -> listFeature.accept(ListFragmentFeature.Wish.ShowCitiesList)
                is ListFragment.UiEvent.ActivityResult ->
                    listFeature.accept(
                        ListFragmentFeature.Wish.ActivityResult(
                            event.requestCode,
                            event.resultCode,
                            event.data
                        )
                    )
            }
        }

    val output: ObservableSource<ListFragment.Model> = Observable
        .wrap(listFeature)
        .mapNotNull(outputMapper)
        .subscribeOn(SchedulersProvider.computation())
        .observeOn(SchedulersProvider.ui())

    val news: ObservableSource<String> = Observable.wrap(listFeature.news)
        .mapNotNull(newsMapper)

    init {
        listFeature.accept(ListFragmentFeature.Wish.Refresh)
    }

    private fun createItems(cities: List<City>): List<CityWeatherItem> =
        cities
            .map {
                CityWeatherItem(
                    imageLoader = imageLoader,
                    city = it,
                    colors = colors,
                    clickListener = { cityId -> router.goTo(WEATHER_DETAILS_SCREEN, cityId) }
                )
            }
}