package com.example.getreuesttest.models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.getreuesttest.pojoWewather.WeatherPojo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.getreuesttest.repositories.MainRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val _weatherPojo = MutableLiveData<WeatherPojo>()

    val weatherPojo: LiveData<WeatherPojo>
        get() = _weatherPojo


    fun getWeather(location: String, units: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            mainRepository.getWeather(location, units)
                .enqueue(object : Callback<WeatherPojo> {
                    override fun onResponse(
                        call: Call<WeatherPojo>,
                        response: Response<WeatherPojo>
                    ) {
                        Log.d("weatherTag", "onResponse: " + response.body().toString())
                        _weatherPojo.postValue(response.body())
                        loading.value = false
                    }

                    override fun onFailure(call: Call<WeatherPojo>, t: Throwable) {
                        Log.d("weatherTag", "onFailure: " + t.message)
                        onError("Error" + t.message)
                    }
                })

        }
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}