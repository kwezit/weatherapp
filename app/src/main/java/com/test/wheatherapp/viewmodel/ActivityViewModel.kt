package com.test.wheatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.wheatherapp.data.WeatherAlertsResp
import com.test.wheatherapp.data.WeatherItemData
import com.test.wheatherapp.repository.WeatherRepositoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: WeatherRepositoryClass): ViewModel() {

    private lateinit var weatherData : WeatherAlertsResp
    private var items = mutableListOf<WeatherItemData>()

    private var currentPhotoId = 1
    private fun generateNewPhotoId(): Int = currentPhotoId++

    val itemValues = MutableLiveData<List<WeatherItemData>>()
    val errorData = MutableLiveData<Boolean>()

    fun getListWeatherData() {

        viewModelScope.launch(Dispatchers.IO) {
            weatherData = repository.getWeatherData()

            if (weatherData.features.isEmpty()) {
                // a very simply treating error.
                // Normally we should use here a Result response or other more complex object with isSuccess check.
                errorData.postValue(true)
                return@launch
            }

            val iterator = items.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (weatherData.features.none { it.properties.id == item.id }) {
                    iterator.remove()
                }
            }

            weatherData.features.forEach { weatherFeature ->
                if (items.none { it.id == weatherFeature.properties.id }) {
                    val item = WeatherItemData(weatherFeature.properties.id,
                        generateNewPhotoId(),
                        weatherFeature.properties.event,
                        weatherFeature.properties.senderName,
                        weatherFeature.properties.effective,
                        weatherFeature.properties.ends)

                    items.add(item)
                }
            }

            itemValues.postValue(items)

        }
    }
}