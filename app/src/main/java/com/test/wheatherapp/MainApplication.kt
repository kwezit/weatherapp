package com.test.wheatherapp

import android.app.Application
import android.content.Context
import com.test.wheatherapp.repository.WeatherRepositoryClass
import com.test.wheatherapp.viewmodel.ActivityViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        bind() from singleton { WeatherRepositoryClass()}
        bind() from provider {
            ActivityViewModelFactory(instance())
        }
    }

    init {
        instance = this
    }

    companion object {
        private var instance : MainApplication? = null
        fun getContext(): Context = instance!!.applicationContext
    }
}