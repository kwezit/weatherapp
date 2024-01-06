package com.test.wheatherapp.repository

import com.test.wheatherapp.MainApplication
import com.test.wheatherapp.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val baseUrl = MainApplication.getContext().getString(R.string.link_base_weather_url)

    val instance: WeatherApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApiService::class.java)
    }
}