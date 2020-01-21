package com.rubeus.simpleweather.features.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rubeus.simpleweather.R
import com.rubeus.simpleweather.features.weather.WeatherViewModel
import com.rubeus.simpleweather.utils.webservice.Status

class WeatherActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        recyclerView = findViewById(R.id.weatherList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        weatherViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                WeatherViewModel::class.java)

        weatherViewModel.getCurrentWeather("paris,fr").observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    if (it.data != null) recyclerView.adapter = WeatherRecyclerAdapter(it.data.list)
                }
                Status.ERROR -> {}
            }

        })
    }
}
