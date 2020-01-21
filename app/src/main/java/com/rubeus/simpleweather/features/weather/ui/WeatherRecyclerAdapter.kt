package com.rubeus.simpleweather.features.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rubeus.simpleweather.R
import com.rubeus.simpleweather.features.weather.model.Weather

class WeatherRecyclerAdapter(private val items: List<Weather>): RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherRecyclerAdapter.WeatherRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_recycler_item, parent, false)
        return WeatherRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WeatherRecyclerAdapter.WeatherRecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class WeatherRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mainWeatherText: TextView = itemView.findViewById(R.id.mainWeather)
        private val descriptionText: TextView = itemView.findViewById(R.id.description)
        private val temperatureText: TextView = itemView.findViewById(R.id.temperature)
        private val temperatureMinMaxText: TextView = itemView.findViewById(R.id.temperatureMinMax)
        private val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)

        fun bind(itemData: Weather) {
            val weatherOverview = if (itemData.overview.isNotEmpty()) itemData.overview[0] else null
            mainWeatherText.text = weatherOverview?.main
            descriptionText.text = weatherOverview?.description

            val weatherDetails = itemData.details
            temperatureText.text = itemView.context.resources.getString(
                R.string.weather_temperature, weatherDetails.temperature
            )
            temperatureMinMaxText.text = itemView.context.resources.getString(
                R.string.weather_temperature_min_max, weatherDetails.temperatureMin, weatherDetails.temperatureMax
            )
        }
    }
}