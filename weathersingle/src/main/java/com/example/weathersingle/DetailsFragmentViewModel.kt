package com.example.weathersingle

import androidx.lifecycle.ViewModel
import com.badoo.mvicore.extension.mapNotNull
import com.example.core.SchedulersProvider
import com.example.core.models.City
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

class DetailsFragmentViewModel(detailsFragmentFeature: DetailsFragmentFeature) :
    ViewModel() {

    private val outputMapper: (DetailsFragmentFeature.State) -> DetailsFragment.Model = {
        DetailsFragment.Model(listOf(), it.isLoading)
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

    private fun createItems(cities: List<City>): List<IFlexible<*>> =
        TODO()
}