package com.example.repository.model.db

import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

const val FORECAST_TABLE = "forecasts"
const val FORECAST_CODE_COLUMN = "code"
const val FORECAST_DAY_COLUMN = "week_day"
const val FORECAST_WIND_COLUMN = "wind_speed"
const val FORECAST_DESCRIPTION_COLUMN = "description"
const val FORECAST_TEMPERATURE_COLUMN = "temperature"
const val FORECAST_HUMIDITY_COLUMN = "humidity"
const val FORECAST_PRESSURE_COLUMN = "pressure"
const val FORECAST_ICON_COLUMN = "icon"
const val FORECAST_CITY_ID_COLUMN = "city_id"

@Entity(
    tableName = FORECAST_TABLE,
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = arrayOf(CITY_ID_COLUMN),
        childColumns = arrayOf(FORECAST_CITY_ID_COLUMN),
        onDelete = CASCADE
    )],
    primaryKeys = [FORECAST_DAY_COLUMN, FORECAST_CITY_ID_COLUMN],
    indices = [Index(value = arrayOf(FORECAST_CITY_ID_COLUMN))]
)

@TypeConverters(ForecastDayConverter::class)
data class WeatherForecast(
    @ColumnInfo(name = FORECAST_CODE_COLUMN)
    val code: Int,

    @ColumnInfo(name = FORECAST_DAY_COLUMN)
    @NonNull
    val forDay: ForecastDay,

    @ColumnInfo(name = FORECAST_DESCRIPTION_COLUMN)
    val description: String,

    @ColumnInfo(name = FORECAST_TEMPERATURE_COLUMN)
    val temperature: Float,

    @ColumnInfo(name = FORECAST_HUMIDITY_COLUMN)
    val humidity: Float,

    @ColumnInfo(name = FORECAST_PRESSURE_COLUMN)
    val pressure: Float,

    @ColumnInfo(name = FORECAST_WIND_COLUMN)
    val windSpeed: Float,

    @ColumnInfo(name = FORECAST_ICON_COLUMN)
    val icon: String,

    @ColumnInfo(name = FORECAST_CITY_ID_COLUMN)
    val forCityId: Long
) {

    enum class ForecastDay {
        CURRENT_WEATHER, TOMORROW, AFTER_TOMORROW, THIRD_DAY
    }
}

class ForecastDayConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun toDay(code: Int): WeatherForecast.ForecastDay = when (code) {
            0 -> WeatherForecast.ForecastDay.CURRENT_WEATHER
            1 -> WeatherForecast.ForecastDay.TOMORROW
            2 -> WeatherForecast.ForecastDay.AFTER_TOMORROW
            3 -> WeatherForecast.ForecastDay.THIRD_DAY
            else -> throw IllegalArgumentException("Unknown day code: $code")
        }

        @JvmStatic
        @TypeConverter
        fun toInt(day: WeatherForecast.ForecastDay): Int = day.ordinal
    }
}