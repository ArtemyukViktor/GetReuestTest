package com.example.getreuesttest.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.getreuesttest.pojoWeather.WeatherPojo
import com.example.getreuesttest.repositories.MainRepository
import com.example.getreuesttest.repositorise.Repository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val mainRepository: MainRepository,
                                application: Application
) : AndroidViewModel(application) {

    private var repository: Repository
    var getWeatherFrom_d_b: LiveData<WeatherPojo>

    init {
        repository = Repository(application)
        getWeatherFrom_d_b = repository.allCats
    }

    fun insert(cats: WeatherPojo?) {
       repository.insert(cats)
    }

    fun getWeatherFrom_d_b(): LiveData<WeatherPojo>? {
        return getWeatherFrom_d_b
    }

    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }


    fun getWeather(location: String, units: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            mainRepository.getWeather(location, units)
                .enqueue(object : Callback<WeatherPojo> {
                    override fun onResponse(
                        call: Call<WeatherPojo>,
                        response: Response<WeatherPojo>
                    ) {
                        Log.d("weatherTag", "onResponse: SERVER " + response.body().toString())
                        insert(response.body())
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