package com.rubeus.simpleweather.features.weather.model

data class WeatherOverview(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)