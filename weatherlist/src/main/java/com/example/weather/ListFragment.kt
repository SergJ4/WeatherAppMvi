package com.example.weather

import com.example.core.BaseFragment
import com.example.weather.adapter.CityWeatherItem
import javax.inject.Inject

class ListFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.city_weather_list_layout

    @Inject
    internal lateinit var viewModel: ListFragmentViewModel

    companion object {
        fun getInstance() = ListFragment()
    }

    sealed class UiEvent {
        object SwipeRefresh : UiEvent()
        object AddCityClick : UiEvent()
    }

    data class Model(val listItems: List<CityWeatherItem>, val isLoading: Boolean)
}