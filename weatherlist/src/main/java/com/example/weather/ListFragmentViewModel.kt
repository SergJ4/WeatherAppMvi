package com.example.weather

import androidx.lifecycle.ViewModel
import com.badoo.mvicore.extension.mapNotNull
import com.example.core.SchedulersProvider
import com.example.core.interfaces.Colors
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.Router
import com.example.core.interfaces.WEATHER_DETAILS_SCREEN
import com.example.core.models.City
import com.example.weather.adapter.CityWeatherItem
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

class ListFragmentViewModel(
    listFeature: ListFragmentFeature,
    private val imageLoader: ImageLoader,
    private val router: Router,
    private val colors: Colors
) : ViewModel() {

    private val mapper: (ListFragmentFeature.State) -> ListFragment.Model = {
        ListFragment.Model(createItems(it.list), it.isLoading)
    }

    val input: Consumer<ListFragment.UiEvent> =
        Consumer { event ->
            when (event) {
                ListFragment.UiEvent.SwipeRefresh -> listFeature.accept(ListFragmentFeature.Wish.Refresh)
                ListFragment.UiEvent.AddCityClick -> listFeature.accept(ListFragmentFeature.Wish.ShowCitiesList)
            }
        }

    val output: ObservableSource<ListFragment.Model> = Observable
        .wrap(listFeature)
        .mapNotNull(mapper)
        .subscribeOn(SchedulersProvider.computation())
        .observeOn(SchedulersProvider.ui())

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