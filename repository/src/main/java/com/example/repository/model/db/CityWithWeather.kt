package com.example.repository.model.db

import androidx.room.Embedded
import androidx.room.Relation

class CityWithWeather {

    @Embedded
    var city: City? = null

    @Relation(parentColumn = CITY_ID_COLUMN, entityColumn = FORECAST_CITY_ID_COLUMN)
    var weather: List<WeatherForecast> = listOf()
}