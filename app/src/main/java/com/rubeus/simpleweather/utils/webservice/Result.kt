package com.rubeus.simpleweather.utils.webservice

class Result<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> clear(): Result<T> {
            return Result(Status.UNKNOWN, null, null)
        }

        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): Result<T> {
            return Result(Status.ERROR, null, msg)
        }

        fun <T> loading(): Result<T> {
            return Result(Status.LOADING, null, null)
        }
    }
}