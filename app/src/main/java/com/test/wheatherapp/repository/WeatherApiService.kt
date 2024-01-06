package com.test.wheatherapp.repository

import com.test.wheatherapp.data.WeatherAlertsResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("alerts/active")
    fun getActiveAlerts(@Query("status") status: String, @Query("message_type") messageType: String): Call<WeatherAlertsResp>
}