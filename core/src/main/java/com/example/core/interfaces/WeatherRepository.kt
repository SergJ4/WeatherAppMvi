package com.example.core.interfaces

import com.example.core.models.City
import io.reactivex.Completable
import io.reactivex.Flowable

interface WeatherRepository {

    fun observeCitiesCurrentWeather(): Flowable<List<City>>

    fun observeExactCityWeather(cityId: Long): Flowable<City>

    fun addCity(cityName: String): Completable

    fun refresh(): Completable
}