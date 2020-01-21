package com.rubeus.simpleweather.features.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rubeus.simpleweather.features.weather.model.WeatherForecast

class WeatherViewModel: ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()

    fun getCurrentWeather(city: String): LiveData<WeatherForecast?> {
        return liveData {
            val response = repository.getForecastByCityName(city)
            emit(response.body())
        }
    }
}