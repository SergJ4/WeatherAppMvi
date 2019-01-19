package com.example.weathersingle.adapter

import android.view.View
import com.example.core.interfaces.ImageLoaderOptions
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.current_weather_detail_item.*

class CurrentDetailsViewHolder(view: View, adapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(view, adapter), LayoutContainer {

    override val containerView: View = view

    fun bind(item: CurrentDetailsItem) {
        cityTitle.text = item.cityName
        weatherDescription.text = item.weatherDescription
        currentTemperature.text = item.temperature
        currentHumidity.text = item.humidity
        currentPressure.text = item.pressure
        currentWind.text = item.wind

        item.imageLoader.load(weatherIcon, ImageLoaderOptions(resourceUri = item.weatherIcon))
    }
}