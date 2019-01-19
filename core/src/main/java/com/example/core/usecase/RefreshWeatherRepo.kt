package com.example.core.usecase

import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.WeatherRepository
import io.reactivex.Completable
import javax.inject.Inject

@FragmentScope
class RefreshWeatherRepo @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    operator fun invoke(): Completable = weatherRepository.refresh()
}