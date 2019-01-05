package com.example.weather.usecase

import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.WeatherRepository
import com.example.core.models.SearchCity
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class AddCity @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(city: SearchCity): Observable<Any> =
        weatherRepository.addCity(city.name).toObservable<Any>()
}