package com.example.repository.datasource.api

import com.example.repository.model.api.ApiCurrentWeatherResponse
import com.example.repository.model.api.ApiForecastWeatherResponse
import com.example.repository.model.api.ApiGroupedWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val CITY_NAME_QUERY_PARAM = "q"
const val CITY_ID_QUERY_PARAM = "id"
const val LANG_QUERY_PARAM = "lang"
const val UNITS_QUERY_PARAM = "units"
const val APP_KEY_QUERY_PARAM = "appid"

const val CURRENT_WEATHER_PATH = "weather"
const val CURRENT_WEATHER_FOR_MULTIPLE_CITIES = "group"
const val FORECAST_WEATHER_PATH = "forecast"

interface WeatherApi {

    @GET(CURRENT_WEATHER_PATH)
    fun fetchCurrentWeatherForCityName(
        @Query(CITY_NAME_QUERY_PARAM) cityName: String,
        @Query(LANG_QUERY_PARAM) languageCode: String,
        @Query(UNITS_QUERY_PARAM) measureUnits: String,
        @Query(APP_KEY_QUERY_PARAM) appKey: String
    ): Single<ApiCurrentWeatherResponse>

    @GET(FORECAST_WEATHER_PATH)
    fun fetchForecastWeatherForCityName(
        @Query(CITY_NAME_QUERY_PARAM) cityName: String,
        @Query(LANG_QUERY_PARAM) languageCode: String,
        @Query(UNITS_QUERY_PARAM) measureUnits: String,
        @Query(APP_KEY_QUERY_PARAM) appKey: String
    ): Single<ApiForecastWeatherResponse>

    @GET(CURRENT_WEATHER_PATH)
    fun fetchCurrentWeatherForCityId(
        @Query(CITY_ID_QUERY_PARAM) cityId: Long,
        @Query(LANG_QUERY_PARAM) languageCode: String,
        @Query(UNITS_QUERY_PARAM) measureUnits: String,
        @Query(APP_KEY_QUERY_PARAM) appKey: String
    ): Single<ApiCurrentWeatherResponse>

    @GET(FORECAST_WEATHER_PATH)
    fun fetchForecastWeatherForCityId(
        @Query(CITY_ID_QUERY_PARAM) cityId: Long,
        @Query(LANG_QUERY_PARAM) languageCode: String,
        @Query(UNITS_QUERY_PARAM) measureUnits: String,
        @Query(APP_KEY_QUERY_PARAM) appKey: String
    ): Single<ApiForecastWeatherResponse>

    @GET(CURRENT_WEATHER_FOR_MULTIPLE_CITIES)
    fun fetchCurrentWeatherForMultipleCities(
        @Query(CITY_ID_QUERY_PARAM) ids: String,
        @Query(LANG_QUERY_PARAM) languageCode: String,
        @Query(UNITS_QUERY_PARAM) measureUnits: String,
        @Query(APP_KEY_QUERY_PARAM) appKey: String
    ): Single<ApiGroupedWeatherResponse>
}