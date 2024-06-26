package com.example.weatherapps

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapps.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var weatherService: WeatherService? = null
    private val apiKey = "bdee480af8557dfd4184046fe43b8558" // Ganti dengan API key Anda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherService = retrofit.create(WeatherService::class.java)

        binding.btnGetWeather.setOnClickListener {
            val cityName = binding.etCityName.text.toString()
            if (cityName.isNotEmpty()){
                getWeather(cityName)
            }else{
                Toast.makeText(this, "masukkan nama kota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeather(cityName: String) {
        val call: Call<WeatherResponse>? = weatherService?.getCurrentWeather(cityName, apiKey)
        call?.enqueue(object : Callback<WeatherResponse?> {
            override fun onResponse(call: Call<WeatherResponse?>, response: Response<WeatherResponse?>) {
                if (response.isSuccessful && response.body() != null) {
                    val weatherResponse: WeatherResponse? = response.body()
                    val weatherInfo = """
                        City: ${weatherResponse?.name}
                        Temperature: ${weatherResponse?.main?.temp}°C
                        Feels Like: ${weatherResponse?.main?.feels_like}°C
                        Humidity: ${weatherResponse?.main?.humidity}%
                    """.trimIndent()
                    binding.tvWeatherInfo.text = weatherInfo
                } else {
                    binding.tvWeatherInfo.text = "Error: Unable to get weather information."
                }
            }

            override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                binding.tvWeatherInfo.text = "Error: " + t.message
            }
        })
    }
}

