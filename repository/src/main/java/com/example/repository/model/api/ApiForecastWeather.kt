package com.example.repository.model.api

import com.google.gson.annotations.SerializedName

data class ApiForecastWeather(

    @SerializedName("main")
    val mainInfo: ApiWeatherMainInfo,

    @SerializedName("weather")
    val weatherDescription: List<ApiWeatherAdditionalInfo>,

    val wind: ApiWind,

    @SerializedName("dt")
    val timestamp: Long
)