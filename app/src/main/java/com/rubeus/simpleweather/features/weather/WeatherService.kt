package com.rubeus.simpleweather.features.weather

import com.rubeus.simpleweather.features.weather.model.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast?appid=1ac77b43d14dc518f43de9d025e7d876")
    suspend fun getForecastByCityName(@Query("q") city: String): Response<WeatherForecast>

    @GET("forecast?appId=1ac77b43d14dc518f43de9d025e7d876")
    suspend fun getForecastByCityId(@Query("id") cityId: Int): Response<WeatherForecast>
}