package com.test.wheatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.wheatherapp.repository.WeatherRepositoryClass

@Suppress("UNCHECKED_CAST")
class ActivityViewModelFactory(private val rp: WeatherRepositoryClass): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivityViewModel(rp) as T
    }
}