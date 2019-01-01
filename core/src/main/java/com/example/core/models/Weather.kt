package com.example.core.models

data class Weather(
    val code: Int,
    val description: String,
    val day: WeatherDay,
    val temperature: Float,
    val humidity: Float,
    val windSpeed: Float,
    val pressure: Float,
    val icon: String
) {

    enum class WeatherDay {
        CURRENT_WEATHER, TOMORROW, AFTER_TOMORROW, THIRD_DAY
    }
}