package com.example.weathersingle.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.Strings
import com.example.core.models.City
import com.example.weathersingle.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible

class CurrentDetailsItem(
    private val city: City,
    internal val imageLoader: ImageLoader,
    private val strings: Strings
) : AbstractFlexibleItem<CurrentDetailsViewHolder>() {

    internal val cityName: String = city.title
    internal val weatherIcon: String = city.currentWeather?.icon ?: ""
    internal val weatherDescription: String = city.currentWeather?.description ?: ""
    internal val temperature: String
        get() {
            val temp = city.currentWeather?.temperature?.toInt() ?: 0
            val result = "$temp\u2103"
            return if (temp > 0) {
                "+$result"
            } else {
                result
            }
        }
    internal val pressure: String
        get() {
            return "${city.currentWeather?.pressure?.toInt()} ${strings.get(R.string.pressure_units)}"
        }
    internal val humidity: String
        get() {
            return "${city.currentWeather?.humidity}%"
        }
    internal val wind: String
        get() {
            return "${city.currentWeather?.windSpeed} ${strings.get(R.string.speed_units)}"
        }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: CurrentDetailsViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) = holder.bind(this)

    override fun equals(other: Any?): Boolean =
        other is CurrentDetailsItem && city == other.city

    override fun hashCode(): Int = city.hashCode()

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): CurrentDetailsViewHolder = CurrentDetailsViewHolder(view, adapter)

    override fun getLayoutRes(): Int = R.layout.current_weather_detail_item
}