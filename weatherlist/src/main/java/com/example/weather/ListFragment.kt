package com.example.weather

import android.os.Bundle
import android.view.View
import com.example.core.BaseFragment
import com.example.weather.adapter.CityWeatherItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.city_weather_list_layout.*
import javax.inject.Inject

class ListFragment : BaseFragment(),
    ObservableSource<ListFragment.UiEvent>,
    Consumer<ListFragment.Model> {

    override val layoutRes: Int = R.layout.city_weather_list_layout

    @Inject
    internal lateinit var viewModel: ListFragmentViewModel

    private val adapter: FlexibleAdapter<CityWeatherItem> = FlexibleAdapter(null, null, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListFragmentBindings(viewLifecycleOwner).setup(this)
    }

    companion object {
        fun getInstance() = ListFragment()
    }

    sealed class UiEvent {
        object SwipeRefresh : UiEvent()
        object AddCityClick : UiEvent()
    }

    override fun subscribe(observer: Observer<in UiEvent>) {
        addCityButton.setOnClickListener { observer.onNext(UiEvent.AddCityClick) }
        swipeRefresh.setOnRefreshListener { observer.onNext(UiEvent.SwipeRefresh) }
    }

    override fun accept(model: Model) {
        swipeRefresh.isRefreshing = model.isLoading
        handleItems(model.listItems)
    }

    private fun handleItems(cities: List<CityWeatherItem>) {
        if (cityList.adapter == null) {
            cityList.adapter = adapter
        }

        adapter.updateDataSet(cities)
    }

    data class Model(val listItems: List<CityWeatherItem>, val isLoading: Boolean)
}