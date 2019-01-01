package com.example.repository.datasource.db

import com.example.repository.model.api.ApiCurrentWeatherResponse
import com.example.repository.model.api.ApiForecastWeatherResponse
import com.example.repository.model.db.City
import com.example.repository.model.db.CityWithWeather
import com.example.repository.model.db.WeatherForecast
import com.example.repository.toDatabaseModel
import io.reactivex.Flowable
import io.reactivex.Single

class DbDataSource(private val weatherDao: WeatherDao) {

    fun fetchCurrentWeather(): Flowable<List<CityWithWeather>> =
        weatherDao
            .getCititesAndWeather()
            .map { cityWithWeatherList ->
                cityWithWeatherList.map { cityWithWeather ->
                    CityWithWeather().apply {
                        city = cityWithWeather.city
                        weather =
                                cityWithWeather.weather.filter { it.forDay == WeatherForecast.ForecastDay.CURRENT_WEATHER }
                    }
                }
            }

    fun fetchForecastWeatherForCityId(cityId: Long): Flowable<CityWithWeather> =
        weatherDao.getWeatherForCityId(cityId)

    fun getCitiesInDb(): Single<List<City>> =
        weatherDao
            .getAllCities()

    fun addCity(city: City) = weatherDao.addCities(city)

    fun insertOrUpdateCurrentWeather(currentWeather: List<ApiCurrentWeatherResponse>) =
        currentWeather
            .map { it.toDatabaseModel() }
            .forEach { weatherDao.insertOrUpdateWeather(it) }

    fun insertOrUpdateForecast(forecast: List<ApiForecastWeatherResponse>) =
        forecast
            .map { it.toDatabaseModel() }
            //we get 3 database weather models from one forecast response
            //3 days forecast
            .forEach { forecastsForCity ->
                forecastsForCity
                    .forEach { weatherDao.insertOrUpdateWeather(it) }
            }
}