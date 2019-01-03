package com.example.weather.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.color
import com.example.core.interfaces.ImageLoader
import com.example.core.models.City
import com.example.core.models.Weather
import com.example.weather.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible

class CityWeatherItem(
    internal val imageLoader: ImageLoader,
    private val city: City,
    private val context: Context,
    internal val clickListener: (cityId: Long) -> Unit
) : AbstractFlexibleItem<CityWeatherViewHolder>() {

    private val currentWeather = city
        .weatherPrediction
        .find { it.day == Weather.WeatherDay.CURRENT_WEATHER }

    internal val cityName = city.title
    internal val cityId = city.id
    internal val weatherDescription = currentWeather?.description ?: ""
    internal val temperature: String
        get() {
            val temp = currentWeather?.temperature?.toInt() ?: 0
            val result = "$temp\u2103"
            return if (temp > 0) {
                "+$result"
            } else {
                result
            }
        }
    internal val icon = currentWeather?.icon ?: ""

    internal val temperatureColor: Int
        get() {
            val temp = currentWeather?.temperature ?: 0f
            return when {
                temp < -10 -> context.color(R.color.temperature_very_cold)
                temp < 10 -> context.color(R.color.temperature_cold)
                temp < 25 -> context.color(R.color.temperature_warm)
                temp >= 25 -> context.color(R.color.temperature_hot)
                else -> context.color(R.color.weather_city_card_temperature_default_color)
            }
        }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: CityWeatherViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) = holder.bind(this)

    override fun equals(other: Any?): Boolean = other is CityWeatherItem &&
            city == other.city

    override fun hashCode(): Int = city.hashCode()

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): CityWeatherViewHolder =
        CityWeatherViewHolder(view, adapter)

    override fun getLayoutRes(): Int = R.layout.city_weather_item
}