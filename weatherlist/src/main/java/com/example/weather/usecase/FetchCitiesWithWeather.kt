package com.example.weather.usecase

import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.WeatherRepository
import com.example.core.models.City
import io.reactivex.Flowable
import javax.inject.Inject

@FragmentScope
class FetchCitiesWithWeather @Inject constructor(private val repository: WeatherRepository) {

    operator fun invoke(): Flowable<List<City>> = repository
        .observeCitiesCurrentWeather()
        .map { citites ->
            citites.filter {
                it.weatherPrediction.isNotEmpty() &&
                        !it.weatherPrediction[0].description.isNullOrBlank()
            }
        }
}