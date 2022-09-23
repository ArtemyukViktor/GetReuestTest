package com.example.getreuesttest.pojoWeather


import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_table", indices = [Index(value = ["id"], unique = true)])
data class WeatherPojo(
    @PrimaryKey @SerializedName("id") @ColumnInfo(name = "id")
    val id: Int,
    val base: String,
    @Embedded
    val clouds: Clouds,
    val cod: Int,
    @Embedded
    val coord: Coord,
    val dt: Int,
    @Embedded
    val main: Main,
    val name: String,
    @Embedded()
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>? = listOf(),
    @Embedded
    val wind: Wind
)