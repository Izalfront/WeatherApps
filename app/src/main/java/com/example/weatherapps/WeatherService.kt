package com.example.weatherapps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
 @GET("data/2.5/weather")
 fun getCurrentWeather(@Query("q") cityName: String, @Query("appid") apikey: String): Call<WeatherResponse>
}

