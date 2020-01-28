package com.rubeus.simpleweather.utils.webservice

sealed class Result

data class Success<T>(val data: T): Result()
data class Error(val exception: Exception): Result()
object Loading: Result()
object Unknown: Result()