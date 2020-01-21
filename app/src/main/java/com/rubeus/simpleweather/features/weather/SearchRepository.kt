package com.rubeus.simpleweather.features.weather

import android.content.Context
import androidx.preference.PreferenceManager

const val LAST_SEARCH_KEY: String = "LAST_SEARCH"

class SearchRepository(context: Context) {
    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getLastSearchRequest(): String? {
        return prefs.getString(LAST_SEARCH_KEY, "")
    }

    fun setLastSearchRequest(searchRequest: String) {
        prefs.edit().putString(LAST_SEARCH_KEY, searchRequest).apply()
    }
}