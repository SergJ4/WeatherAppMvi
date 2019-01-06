package com.example.weathersingle

import androidx.lifecycle.ViewModel
import com.badoo.mvicore.extension.mapNotNull
import com.example.core.SchedulersProvider
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.Strings
import com.example.core.models.City
import com.example.core.models.Weather
import com.example.weathersingle.adapter.CurrentDetailsItem
import com.example.weathersingle.adapter.ForecastDetailsItem
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

class DetailsFragmentViewModel(
    detailsFragmentFeature: DetailsFragmentFeature,
    private val imageLoader: ImageLoader,
    private val strings: Strings
) :
    ViewModel() {

    init {
        detailsFragmentFeature.accept(DetailsFragmentFeature.Wish.LoadCity)
    }

    private val outputMapper: (DetailsFragmentFeature.State) -> DetailsFragment.Model = {
        DetailsFragment.Model(createItems(it.currentCity), it.isLoading)
    }

    private val newsMapper: (DetailsFragmentFeature.News) -> String = {
        strings.unknownErrorString()
    }

    val input: Consumer<DetailsFragment.UiEvent> = Consumer { event ->
        when (event) {
            DetailsFragment.UiEvent.Refresh -> detailsFragmentFeature.accept(DetailsFragmentFeature.Wish.Refresh)
            DetailsFragment.UiEvent.BackClicked -> detailsFragmentFeature.accept(
                DetailsFragmentFeature.Wish.Back
            )
        }
    }

    val output: ObservableSource<DetailsFragment.Model> = Observable
        .wrap(detailsFragmentFeature)
        .mapNotNull(outputMapper)
        .subscribeOn(SchedulersProvider.computation())
        .observeOn(SchedulersProvider.ui())

    val news: ObservableSource<String> = Observable.wrap(detailsFragmentFeature.news)
        .mapNotNull(newsMapper)

    private fun createItems(city: City?): List<AbstractFlexibleItem<*>> {
        if (city == null) {
            return listOf()
        }

        val result = mutableListOf<AbstractFlexibleItem<*>>()

        result.add(CurrentDetailsItem(city, imageLoader, strings))

        val forecastItems = city
            .weatherPrediction
            .asSequence()
            .filter {
                it.day != Weather.WeatherDay.CURRENT_WEATHER
            }
            .map {
                ForecastDetailsItem(it, imageLoader, strings)
            }
            .toList()

        result.addAll(forecastItems)
        return result
    }
}