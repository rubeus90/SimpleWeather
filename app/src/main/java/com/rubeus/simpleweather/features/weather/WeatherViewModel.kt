package com.rubeus.simpleweather.features.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rubeus.simpleweather.features.weather.model.WeatherForecast
import com.rubeus.simpleweather.utils.webservice.Result

class WeatherViewModel: ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()

    fun getCurrentWeather(city: String): LiveData<Result<WeatherForecast>> {
        return liveData {
            emit(Result.loading())

            try {
                val data = repository.getForecastByCityName(city)
                if (data.isSuccessful) {
                    emit(Result.success(data.body()))
                } else {
                    emit(Result.error(data.message()))
                }
            } catch (e: Exception) {
                emit(Result.error(e.toString()))
            }
        }
    }
}