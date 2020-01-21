package com.rubeus.simpleweather.utils.webservice

import android.util.Log
import com.google.gson.GsonBuilder
import com.rubeus.simpleweather.features.weather.WeatherService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Webservice {
    private val webservice: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Interceptor {
                val request: Request = it.request().newBuilder()
                    .url(it.request().url().newBuilder()
                        .addQueryParameter("appid", "1ac77b43d14dc518f43de9d025e7d876")
                        .build()
                    )
                    .build()
                Log.d("WS request", request.url().toString())
                it.proceed(request)
            })
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    fun getWeatherWebservice(): WeatherService {
        return webservice.create(WeatherService::class.java)
    }
}