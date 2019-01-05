package com.example.repository.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CITY_TABLE = "cities"
const val CITY_ID_COLUMN = "id"
const val CITY_NAME_COLUMN = "name"

@Entity(tableName = CITY_TABLE)
data class City(
    @PrimaryKey
    @ColumnInfo(name = CITY_ID_COLUMN)
    val id: Long,

    @ColumnInfo(name = CITY_NAME_COLUMN)
    val name: String
)