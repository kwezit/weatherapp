package com.test.wheatherapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.wheatherapp.data.WeatherAlertsResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepositoryClass {

    fun getWeatherData() = try {
        val response = RetrofitClient.instance.getActiveAlerts("actual", "alert").execute()
        if (response.isSuccessful) {
            response.body() ?: WeatherAlertsResp(emptyList())
        } else {
            WeatherAlertsResp(emptyList())
        }
    } catch (e: Exception) {
        WeatherAlertsResp(emptyList())
    }

    @Suppress("UNUSED")
    fun getFeatures(): LiveData<WeatherAlertsResp> {
        val data = MutableLiveData<WeatherAlertsResp>()

        RetrofitClient.instance.getActiveAlerts("actual", "alert")
            .enqueue(object : Callback<WeatherAlertsResp> {
                override fun onResponse(
                    call: Call<WeatherAlertsResp>,
                    response: Response<WeatherAlertsResp>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    } else {
                        data.value = WeatherAlertsResp(emptyList())
                    }
                }

                override fun onFailure(call: Call<WeatherAlertsResp>, t: Throwable) {
                    data.value = WeatherAlertsResp(emptyList())
                }
            })

        return data
    }
}