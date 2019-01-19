package com.example.repository.model.api

import com.google.gson.annotations.SerializedName

data class ApiCurrentWeatherResponse(
    @SerializedName("main")
    val mainInfo: ApiWeatherMainInfo,

    @SerializedName("weather")
    val weatherDescription: List<ApiWeatherAdditionalInfo>,

    val wind: ApiWind,

    @SerializedName("id")
    val cityId: Long,

    @SerializedName("name")
    val cityName: String
)

val currentWeatherError = ApiCurrentWeatherResponse(
    ApiWeatherMainInfo(0f, 0f, 0f),
    listOf(),
    ApiWind(0f),
    cityId = -1,
    cityName = "error"
)