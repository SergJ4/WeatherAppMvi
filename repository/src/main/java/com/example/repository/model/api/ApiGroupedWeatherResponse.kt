package com.example.repository.model.api

import com.google.gson.annotations.SerializedName

data class ApiGroupedWeatherResponse(
    @SerializedName("list")
    val weatherList: List<ApiCurrentWeatherResponse>
)