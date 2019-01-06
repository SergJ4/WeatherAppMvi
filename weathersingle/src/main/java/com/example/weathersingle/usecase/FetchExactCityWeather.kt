package com.example.weathersingle.usecase

import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.WeatherRepository
import com.example.core.models.City
import io.reactivex.Flowable
import javax.inject.Inject

@FragmentScope
class FetchExactCityWeather @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(cityId: Long): Flowable<City> =
        weatherRepository.observeExactCityWeather(cityId)
}