package com.example.getreuesttest.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.getreuesttest.pojoWeather.WeatherPojo
import com.example.getreuesttest.room.AppDao
import com.example.getreuesttest.room.AppDatabase

class Repository(application: Application?) {
    var appDao: AppDao
    var getWeatherFrom_d_b: LiveData<WeatherPojo>
    private val database: AppDatabase
    init {
        database = AppDatabase.getInstance(application)!!
        appDao = database.catimgDao()
        getWeatherFrom_d_b = appDao.getcats()
    }

    fun insert(cats: WeatherPojo?) {

        InsertAsyncTask(appDao).execute(cats)
    }

    val allCats: LiveData<WeatherPojo>
        get() = getWeatherFrom_d_b


    private class InsertAsyncTask(private val appDao: AppDao) :
        AsyncTask<WeatherPojo?, Void?, Void?>() {
        override fun doInBackground(vararg p0: WeatherPojo?): Void? {
            appDao.insert(p0[0])
            return null
        }

    }



}