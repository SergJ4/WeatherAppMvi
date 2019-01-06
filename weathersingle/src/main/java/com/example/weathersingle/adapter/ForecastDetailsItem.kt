package com.example.weathersingle.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.toFormattedString
import com.example.core.interfaces.ImageLoader
import com.example.core.models.Weather
import com.example.weathersingle.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import java.util.*

class ForecastDetailsItem(
    private val weather: Weather,
    internal val imageLoader: ImageLoader,
    internal val context: Context
) : AbstractFlexibleItem<ForecastDetailsViewHolder>() {

    internal val weatherDate: String
        get() {
            val today = Calendar.getInstance()
            today.add(Calendar.DAY_OF_MONTH, weather.day.ordinal)
            return today.toFormattedString()
        }
    internal val weatherIcon: String = weather.icon
    internal val weatherDescription: String = weather.description
    internal val temperature: String
        get() {
            val temp = weather.temperature.toInt()
            val result = "$temp\u2103"
            return if (temp > 0) {
                "+$result"
            } else {
                result
            }
        }
    internal val pressure: String
        get() {
            return "${weather.pressure.toInt()} ${context.getString(R.string.pressure_units)}"
        }
    internal val humidity: String
        get() {
            return "${weather.humidity}%"
        }
    internal val wind: String
        get() {
            return "${weather.windSpeed} ${context.getString(R.string.speed_units)}"
        }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ForecastDetailsViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) = holder.bind(this)

    override fun equals(other: Any?): Boolean =
        other is ForecastDetailsItem && weather == other.weather

    override fun hashCode(): Int = weather.hashCode()

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ForecastDetailsViewHolder = ForecastDetailsViewHolder(view, adapter)

    override fun getLayoutRes(): Int = R.layout.forecast_weather_detail_item
}