package com.example.weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.core.BaseFragment
import com.example.core.interfaces.Logger
import com.example.core.interfaces.Translator
import com.example.core.models.SearchCity
import com.example.weather.adapter.CityWeatherItem
import com.example.weather.usecase.GooglePlaces
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.city_weather_list_layout.*
import javax.inject.Inject

class ListFragment : BaseFragment(),
    ObservableSource<ListFragment.UiEvent>,
    Consumer<ListFragment.Model> {

    override val layoutRes: Int = R.layout.city_weather_list_layout

    @Inject
    internal lateinit var viewModel: ListFragmentViewModel
    @Inject
    internal lateinit var logger: Logger
    @Inject
    internal lateinit var translator: Translator

    private lateinit var observer: Observer<in UiEvent>
    private lateinit var googlePlacesDisposable: Disposable

    //Hate to integrate with third-party libraries! Must be here, because it needs "fresh" fragment instance
    private lateinit var googlePlaces: GooglePlaces

    internal val newsConsumer: Consumer<String> = Consumer { showMessage(it) }

    private val adapter: FlexibleAdapter<CityWeatherItem> = FlexibleAdapter(null, null, true)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        googlePlaces = GooglePlaces(logger, translator, this)
            .apply {
                googlePlacesDisposable = selectedCity().subscribe {
                    observer.onNext(UiEvent.CityChosen(it))
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListFragmentBindings(viewLifecycleOwner).setup(this)
        addCityButton.setOnClickListener { googlePlaces.showCitiesList() }
    }

    companion object {
        fun getInstance() = ListFragment()
    }

    sealed class UiEvent {
        object SwipeRefresh : UiEvent()
        data class CityChosen(val city: SearchCity) : UiEvent()
    }

    override fun subscribe(observer: Observer<in UiEvent>) {
        this.observer = observer
        swipeRefresh.setOnRefreshListener { observer.onNext(UiEvent.SwipeRefresh) }
    }

    override fun accept(model: Model) {
        swipeRefresh.isRefreshing = model.isLoading
        handleItems(model.listItems)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        googlePlaces.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleItems(cities: List<CityWeatherItem>) {
        if (cityList.adapter == null) {
            cityList.adapter = adapter
        }

        adapter.updateDataSet(cities)
    }

    data class Model(val listItems: List<CityWeatherItem>, val isLoading: Boolean)

    override fun onDestroy() {
        super.onDestroy()
        googlePlacesDisposable.dispose()
    }
}