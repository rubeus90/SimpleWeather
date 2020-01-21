package com.rubeus.simpleweather.features.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rubeus.simpleweather.R
import com.rubeus.simpleweather.features.weather.model.Weather
import java.util.*

class WeatherRecyclerAdapter(private val items: List<Weather>): RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_recycler_item, parent, false)
        return WeatherRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WeatherRecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class WeatherRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateText: TextView = itemView.findViewById(R.id.date)
        private val mainWeatherText: TextView = itemView.findViewById(R.id.mainWeather)
        private val descriptionText: TextView = itemView.findViewById(R.id.description)
        private val temperatureText: TextView = itemView.findViewById(R.id.temperature)
        private val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)

        private val sdf = java.text.SimpleDateFormat("EE dd MMMM YYYY - HH:mm", Locale.CANADA)

        fun bind(itemData: Weather) {
            dateText.text = sdf.format(itemData.date * 1000)

            val weatherOverview = if (itemData.overview.isNotEmpty()) itemData.overview[0] else null
            mainWeatherText.text = weatherOverview?.main
            descriptionText.text = weatherOverview?.description

            val weatherDetails = itemData.details
            temperatureText.text = itemView.context.resources.getString(
                R.string.weather_temperature, weatherDetails.temperature
            )
        }
    }
}