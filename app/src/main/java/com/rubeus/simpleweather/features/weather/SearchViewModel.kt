package com.rubeus.simpleweather.features.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SearchRepository by lazy { SearchRepository(application) }

    val lastSearchData: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply { postValue(repository.getLastSearchRequest()) }
    }

    fun saveLastSearchData(searchRequest: String) {
        repository.setLastSearchRequest(searchRequest)
    }
}