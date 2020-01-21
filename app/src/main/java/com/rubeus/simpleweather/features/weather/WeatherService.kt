package com.rubeus.simpleweather.features.weather

import com.rubeus.simpleweather.features.weather.model.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast?units=metric")
    suspend fun getForecastByCityName(@Query("q") city: String): Response<WeatherForecast>

    @GET("forecast?units=metric")
    suspend fun getForecastByCityId(@Query("id") cityId: Int): Response<WeatherForecast>
}