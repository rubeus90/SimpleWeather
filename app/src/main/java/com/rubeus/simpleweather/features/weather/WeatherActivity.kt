package com.rubeus.simpleweather.features.weather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rubeus.simpleweather.R

class WeatherActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        weatherViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                WeatherViewModel::class.java)

        weatherViewModel.getCurrentWeather("paris,fr").observe(this, Observer {
            Log.d("WS request", it.toString())
        })
    }
}
