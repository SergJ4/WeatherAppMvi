package com.example.repository.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repository.model.db.City
import com.example.repository.model.db.WeatherForecast

const val DB_NAME = "weather_db"

@Database(version = 1, entities = [City::class, WeatherForecast::class])
abstract class WeatherDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}