package com.example.getreuesttest.api

import com.example.getreuesttest.pojoWewather.WeatherPojo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?q=Kiev,UA&appid=e1ef7dee05dd4b3c5a45c2dab48cb87a&units=metric
interface WebApi {
@GET("data/2.5/weather")
fun getWeather(
    @Query("q") location: String,
    @Query("units") units: String,
): Call<WeatherPojo>

    companion object {
        operator fun invoke(
            connectivityInterceptor: Interceptor
        ): WebApi {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("appid", "e1ef7dee05dd4b3c5a45c2dab48cb87a")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)

            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebApi::class.java)
        }
    }

}