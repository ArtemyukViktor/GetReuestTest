package com.example.getreuesttest.pojoWeather

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName


data class Sys(
    val country: String,
    @SerializedName("id")
    val id_: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)