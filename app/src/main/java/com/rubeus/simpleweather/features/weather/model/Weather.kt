package com.rubeus.simpleweather.features.weather.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("dt") val date: Long,
    @SerializedName("weather") val overview: List<WeatherOverview>,
    @SerializedName("main") val details: WeatherDetails
)