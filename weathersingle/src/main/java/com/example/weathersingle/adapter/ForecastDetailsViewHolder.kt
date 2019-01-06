package com.example.weathersingle.adapter

import android.view.View
import com.example.core.interfaces.ImageLoaderOptions
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.forecast_weather_detail_item.*

class ForecastDetailsViewHolder(view: View, adapter: FlexibleAdapter<*>) :
    FlexibleViewHolder(view, adapter), LayoutContainer {

    override val containerView: View = view

    fun bind(item: ForecastDetailsItem) {
        weatherDate.text = item.weatherDate
        weatherDescription.text = item.weatherDescription
        temperature.text = item.temperature
        humidity.text = item.humidity
        pressure.text = item.pressure
        wind.text = item.wind

        item.imageLoader.load(weatherIcon, ImageLoaderOptions(resourceUri = item.weatherIcon))
    }
}