package com.example.repository.model.api

import com.google.gson.annotations.SerializedName

data class ApiWeatherMainInfo(
    @SerializedName("temp")
    val temperature: Float,

    val pressure: Float,
    val humidity: Float
)