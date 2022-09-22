package com.example.getreuesttest.repositories

import com.example.getreuesttest.api.WebApi

class MainRepository constructor(private val webApi: WebApi) {

     fun getWeather(location :String, units: String) = webApi.getWeather(location,units)
}