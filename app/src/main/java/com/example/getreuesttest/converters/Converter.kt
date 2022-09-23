package com.example.getreuesttest.converters

import androidx.room.TypeConverter
import com.example.getreuesttest.pojoWeather.Main
import com.example.getreuesttest.pojoWeather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converter {
    @TypeConverter
    fun stringToListServer(data: String?): List<Weather?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<Weather?>?>() {}.type
        return Gson().fromJson<List<Weather?>>(data, listType)
    }

    @TypeConverter
    fun listServerToString(someObjects: List<Weather?>?): String? {
        return Gson().toJson(someObjects)
    }
}