package com.example.weather.adapter

import android.view.View
import com.example.core.interfaces.ImageLoaderOptions
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.city_weather_item.*

class CityWeatherViewHolder(view: View, adapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(view, adapter), LayoutContainer {

    override val containerView: View = view

    fun bind(cityWeatherItem: CityWeatherItem) {
        cityTitle.text = cityWeatherItem.cityName
        weatherDescription.text = cityWeatherItem.weatherDescription
        temperatureText.text = cityWeatherItem.temperature
        temperatureText.setTextColor(cityWeatherItem.temperatureColor)
        cityWeatherItem
            .imageLoader
            .load(weatherIcon, ImageLoaderOptions(resourceUri = cityWeatherItem.icon))

        cityWeatherCard
            .setOnClickListener { cityWeatherItem.clickListener(cityWeatherItem.cityId) }
    }
}