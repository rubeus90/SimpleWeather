package com.rubeus.simpleweather.features.search

import android.content.SearchRecentSuggestionsProvider

class SearchSuggestionsProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.rubeus.simpleweather.features.search.SearchSuggestionsProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}