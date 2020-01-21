package com.rubeus.simpleweather.utils.webservice

import com.google.gson.GsonBuilder
import com.rubeus.simpleweather.features.weather.WeatherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Webservice {
    private val webservice: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    fun getWeatherWebservice(): WeatherService {
        return webservice.create(WeatherService::class.java)
    }
}