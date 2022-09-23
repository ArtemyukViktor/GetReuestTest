package com.example.getreuesttest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.getreuesttest.pojoWeather.WeatherPojo

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherPojo: WeatherPojo?)

    @Query("SELECT DISTINCT * FROM weather_table")
    fun getLiveDataWeather(): LiveData<WeatherPojo>

    @Query("DELETE FROM weather_table")
    fun deleteAll()
}
