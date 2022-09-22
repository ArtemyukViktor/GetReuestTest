package com.example.getreuesttest.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.getreuesttest.R
import com.example.getreuesttest.api.WebApi
import com.example.getreuesttest.interceptors.ConnectivityInterceptorImpl
import com.example.getreuesttest.models.MainViewModel
import com.example.getreuesttest.repositories.MainRepository
import com.example.getreuesttest.models.MyViewModelFactory

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.tv_message)
        val progressBar: ProgressBar = root.findViewById(R.id.progressBar)
        val apiService = WebApi(ConnectivityInterceptorImpl(this.requireContext()))

        val mainRepository = MainRepository(apiService)

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

        viewModel.weatherPojo.observe(viewLifecycleOwner) {
            textView.text = it.main.temp.toString()
        }

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

        viewModel.getWeather("Kiev,UA", "metric")

        return root
    }

}