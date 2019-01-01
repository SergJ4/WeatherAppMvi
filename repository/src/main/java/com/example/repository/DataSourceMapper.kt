package com.example.repository

import com.example.repository.model.api.ApiCity
import com.example.repository.model.api.ApiCurrentWeatherResponse
import com.example.repository.model.api.ApiForecastWeather
import com.example.repository.model.api.ApiForecastWeatherResponse
import com.example.repository.model.db.WeatherForecast
import java.util.*

private const val HOUR = 60 * 60

fun ApiCurrentWeatherResponse.toDatabaseModel(): WeatherForecast =
    WeatherForecast(
        code = weatherDescription[0].weatherCode,
        forDay = WeatherForecast.ForecastDay.CURRENT_WEATHER,
        description = weatherDescription[0].description,
        temperature = mainInfo.temperature,
        humidity = mainInfo.humidity,
        pressure = mainInfo.pressure.toMmHgSt(),
        windSpeed = wind.speed,
        icon = weatherDescription[0].icon,
        forCityId = cityId
    )

fun ApiForecastWeatherResponse.toDatabaseModel(): List<WeatherForecast> =
    forecasts
        .asSequence()
        .filter { it.timestamp.isMidday }
        .take(3)
        .map {
            it.toDatabaseModel(city)
        }
        .toList()

fun ApiForecastWeather.toDatabaseModel(city: ApiCity): WeatherForecast =
    WeatherForecast(
        code = weatherDescription[0].weatherCode,
        forDay = timestamp.forecastDay,
        description = weatherDescription[0].description,
        temperature = mainInfo.temperature,
        humidity = mainInfo.humidity,
        pressure = mainInfo.pressure.toMmHgSt(),
        windSpeed = wind.speed,
        icon = weatherDescription[0].icon,
        forCityId = city.id
    )

private val Long.forecastDay: WeatherForecast.ForecastDay
    get() {
        val nowInSeconds = Date().time / 1000
        return when {
            this - nowInSeconds <= 24 * HOUR -> WeatherForecast.ForecastDay.TOMORROW
            this - nowInSeconds <= 48 * HOUR -> WeatherForecast.ForecastDay.AFTER_TOMORROW
            this - nowInSeconds <= 72 * HOUR -> WeatherForecast.ForecastDay.THIRD_DAY
            else -> throw IllegalStateException("Forecast day can be returned only for three days")
        }
    }

private val Long.isMidday: Boolean
    get() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * 1000

        return calendar.get(Calendar.HOUR_OF_DAY) in (11..13)
    }

//convert from hPa to mmHg St
private fun Float.toMmHgSt(): Float = this * 0.75f
