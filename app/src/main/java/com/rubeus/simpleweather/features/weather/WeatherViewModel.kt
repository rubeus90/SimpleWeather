package com.rubeus.simpleweather.features.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubeus.simpleweather.features.weather.model.WeatherForecast
import com.rubeus.simpleweather.utils.webservice.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val repository: WeatherRepository by lazy { WeatherRepository() }

    private val forecastData: MutableLiveData<Result<WeatherForecast>> by lazy {
        MutableLiveData<Result<WeatherForecast>>().apply { postValue(Result.clear()) }
    }

    fun getWeatherForecast(): LiveData<Result<WeatherForecast>> = forecastData

    fun fetchWeatherForecast(city: String) {
         viewModelScope.launch(Dispatchers.IO) {
            forecastData.postValue(Result.loading())

            try {
                val data = repository.getForecastByCityName(city)
                if (data.isSuccessful) {
                    forecastData.postValue(Result.success(data.body()))
                } else {
                    forecastData.postValue(Result.error(data.message()))
                }
            } catch (e: Exception) {
                forecastData.postValue(Result.error(e.toString()))
            }
        }
    }
}