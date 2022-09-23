package com.example.getreuesttest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.getreuesttest.R
import com.example.getreuesttest.api.WebApi
import com.example.getreuesttest.interceptors.ConnectivityInterceptorImpl
import com.example.getreuesttest.models.MainViewModel
import com.example.getreuesttest.models.MyViewModelFactory
import com.example.getreuesttest.repositories.MainRepository

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        textView = root.findViewById(R.id.tv_message)
        progressBar = root.findViewById(R.id.progressBar)
        val apiService = WebApi(ConnectivityInterceptorImpl(this.requireContext()))

        val mainRepository = MainRepository(apiService)
        viewModel =
            ViewModelProvider(
                this, MyViewModelFactory(
                    mainRepository, requireActivity().application
                )
            )[MainViewModel::class.java]

        observable()
        makeRequest()
        return root
    }

    private fun observable() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        viewModel.getWeatherFrom_d_b()?.observe(
            viewLifecycleOwner
        ) { weather ->
            textView.text = weather.main.temp.toString()
            Log.d("weatherTag", "onChanged DB: $weather")
        }
    }

    private fun makeRequest() {
        viewModel.getWeather("Kiev,UA", "metric")
    }

}