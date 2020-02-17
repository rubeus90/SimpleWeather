package com.rubeus.simpleweather.features.weather.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rubeus.simpleweather.R
import com.rubeus.simpleweather.features.search.SearchSuggestionsProvider
import com.rubeus.simpleweather.features.search.SearchViewModel
import com.rubeus.simpleweather.features.weather.WeatherViewModel
import com.rubeus.simpleweather.features.weather.model.WeatherForecast
import com.rubeus.simpleweather.utils.webservice.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchField: SearchView
    private lateinit var loadingView: ProgressBar
    private lateinit var errorView: TextView
    private lateinit var emptyView: TextView

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        recyclerView = findViewById(R.id.weatherList)
        searchField = findViewById(R.id.search)
        loadingView = findViewById(R.id.loading)
        errorView = findViewById(R.id.error)
        emptyView = findViewById(R.id.empty)


        initSearch()
        initWeather()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                fetchWeather(query)
            }
        }
    }

    private fun initSearch() {
        // Configure search manager which provides search suggestions based on recent search queries
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchField.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        // Init the search field with last search query (if applicable) on app launch
        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            SearchViewModel::class.java)
        searchViewModel.lastSearchData.observe(this, Observer {
            searchField.setQuery(it, true)
        })
    }

    private fun initWeather() {
        // Init layout manager to display the weather list
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe weather data and update the UI accordingly
        weatherViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            WeatherViewModel::class.java)
        weatherViewModel.getWeatherForecast().observe(this, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(result: Result) {
        when (result) {
            Unknown -> {
                emptyView.visibility = View.VISIBLE
                loadingView.visibility = View.GONE
                errorView.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            Loading -> {
                emptyView.visibility = View.GONE
                loadingView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            is Success<*> -> {
                result.data?.let {
                    emptyView.visibility = View.GONE
                    loadingView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    val forecast = result.data as WeatherForecast
                    recyclerView.adapter = WeatherRecyclerAdapter(forecast.list)
                }
            }
            is Error -> {
                emptyView.visibility = View.GONE
                loadingView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
    }

    private fun fetchWeather(query: String) {
        if (query.isNotEmpty()) {
            // Close keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(searchField.windowToken, 0)

            weatherViewModel.fetchWeatherForecast(query)
            searchViewModel.saveLastSearchData(query)

            SearchRecentSuggestions(this, SearchSuggestionsProvider.AUTHORITY, SearchSuggestionsProvider.MODE)
                .saveRecentQuery(query, null)
        }
    }
}
