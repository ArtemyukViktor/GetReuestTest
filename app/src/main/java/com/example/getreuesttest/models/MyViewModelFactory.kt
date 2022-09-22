package com.example.getreuesttest.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getreuesttest.repositories.MainRepository

class MyViewModelFactory constructor(private val repository: MainRepository, private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository,this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}