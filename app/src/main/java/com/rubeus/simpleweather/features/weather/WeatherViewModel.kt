package com.rubeus.simpleweather.features.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubeus.simpleweather.utils.webservice.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val repository: WeatherRepository by lazy { WeatherRepository() }

    private val forecastData: MutableLiveData<Result> by lazy {
        MutableLiveData<Result>().apply { postValue(Unknown) }
    }

    fun getWeatherForecast(): LiveData<Result> = forecastData

    fun fetchWeatherForecast(city: String) {
         viewModelScope.launch(Dispatchers.IO) {
            forecastData.postValue(Loading)

            try {
                val data = repository.getForecastByCityName(city)
                if (data.isSuccessful) {
                    forecastData.postValue(Success(data.body()))
                } else {
                    forecastData.postValue(Error(Exception(data.message())))
                }
            } catch (e: Exception) {
                forecastData.postValue(Error(e))
            }
        }
    }
}