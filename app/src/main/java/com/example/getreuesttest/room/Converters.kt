package com.example.getreuesttest.room

import androidx.room.TypeConverter
import com.example.getreuesttest.pojoWeather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {

    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<Weather> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Weather>>() {

        }.type

        return gson.fromJson<List<Weather>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<Weather>): String {
        return gson.toJson(someObjects)
    }

//    @TypeConverter
//    fun fromString(value: String): List<String> {
//        val listType = object : TypeToken<List<String>>() {
//
//        }.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromArrayList(list: List<String>): String {
//        val gson = Gson()
//        return gson.toJson(list)
//    }
}