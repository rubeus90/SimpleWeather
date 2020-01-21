package com.rubeus.simpleweather.features.weather

import com.rubeus.simpleweather.utils.webservice.Webservice

class WeatherRepository {
    private val webservice: WeatherService by lazy {
        Webservice.getWeatherWebservice()
    }

    suspend fun getForecastByCityName(city: String) = webservice.getForecastByCityName(city)
}