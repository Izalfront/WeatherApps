package com.example.weatherapps


data class WeatherResponse(
    val name: String,
    val main: Main
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)


