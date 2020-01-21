package com.rubeus.simpleweather.features.weather.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rubeus.simpleweather.R
import com.rubeus.simpleweather.features.weather.WeatherViewModel
import com.rubeus.simpleweather.features.weather.model.WeatherForecast
import com.rubeus.simpleweather.utils.webservice.Result
import com.rubeus.simpleweather.utils.webservice.Status

class WeatherActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cityNameField: EditText
    private lateinit var submitButton: Button
    private lateinit var loadingView: ProgressBar
    private lateinit var errorView: TextView
    private lateinit var emptyView: TextView

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        recyclerView = findViewById(R.id.weatherList)
        cityNameField = findViewById(R.id.cityName)
        submitButton = findViewById(R.id.submitButton)
        loadingView = findViewById(R.id.loading)
        errorView = findViewById(R.id.error)
        emptyView = findViewById(R.id.empty)

        recyclerView.layoutManager = LinearLayoutManager(this)

        weatherViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            WeatherViewModel::class.java)

        submitButton.setOnClickListener {
            fetchWeather()
        }

        cityNameField.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    fetchWeather()
                    return@OnEditorActionListener true
                }
                else -> {return@OnEditorActionListener true}
            }
        })

        weatherViewModel.getWeatherForecast().observe(this, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(data: Result<WeatherForecast>) {
        when (data.status) {
            Status.UNKNOWN -> {
                emptyView.visibility = View.VISIBLE
                loadingView.visibility = View.GONE
                errorView.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            Status.LOADING -> {
                emptyView.visibility = View.GONE
                loadingView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
            Status.SUCCESS -> {
                if (data.data != null) {
                    emptyView.visibility = View.GONE
                    loadingView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    recyclerView.adapter = WeatherRecyclerAdapter(data.data.list)
                }
            }
            Status.ERROR -> {
                emptyView.visibility = View.GONE
                loadingView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
    }

    private fun fetchWeather() {
        if (cityNameField.text.toString().isNotEmpty()) {
            // Close keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(cityNameField.windowToken, 0)

            weatherViewModel.fetchWeatherForecast(cityNameField.text.toString())
        }
    }
}
