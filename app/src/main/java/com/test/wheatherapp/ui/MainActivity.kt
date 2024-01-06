package com.test.wheatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.wheatherapp.R
import com.test.wheatherapp.databinding.ActivityMainBinding
import com.test.wheatherapp.ui.recycler.MainRecycler
import com.test.wheatherapp.viewmodel.ActivityViewModel
import com.test.wheatherapp.viewmodel.ActivityViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ActivityViewModelFactory by instance()

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[ActivityViewModel::class.java]

        initRecycler()

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        viewModel.itemValues.observe(this) {
            (binding.recyclerView.adapter as MainRecycler).items = it
            binding.progressLoading.isVisible = false
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.errorData.observe(this) {
            binding.progressLoading.isVisible = false
            binding.swipeRefreshLayout.isRefreshing = false
            Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
        }

        refreshData()
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MainRecycler()
        binding.progressLoading.isVisible = true
    }

    private fun refreshData() {
        viewModel.getListWeatherData()
    }
}