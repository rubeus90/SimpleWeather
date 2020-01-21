package com.rubeus.simpleweather.features.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherDetails(
    @SerializedName("temp") val temperature: Double,
    val pressure: Double,
    val humidity: Double,
    @SerializedName("temp_min") val temperatureMin: Double,
    @SerializedName("temp_max") val temperatureMax: Double
)