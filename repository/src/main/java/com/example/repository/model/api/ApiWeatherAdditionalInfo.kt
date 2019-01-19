package com.example.repository.model.api

import com.google.gson.annotations.SerializedName

data class ApiWeatherAdditionalInfo(
    @SerializedName("id")
    val weatherCode: Int,
    val description: String,
    val icon: String
)