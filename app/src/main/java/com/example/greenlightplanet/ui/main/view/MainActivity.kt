package com.example.greenlightplanet.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenlightplanet.R
import com.example.greenlightplanet.model.Performance
import com.example.greenlightplanet.ui.main.adapter.ListAdapter
import com.example.greenlightplanet.ui.main.viewmodel.MainViewModel
import com.example.greenlightplanet.utility.Status.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupObserver()

    }

    private fun setupUI() {
        zoneListRV?.layoutManager = LinearLayoutManager(this)
        adapter = ListAdapter(arrayListOf())
        zoneListRV?.addItemDecoration(
            DividerItemDecoration(
                zoneListRV?.context,
                (zoneListRV?.layoutManager as LinearLayoutManager).orientation
            )
        )
        zoneListRV?.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getPerformanceByZoneList().observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    listProgressBar?.visibility = View.GONE
                    it.data?.let { performance -> renderList(listOf(performance)) }
                    zoneListRV?.visibility = View.VISIBLE
                }
                LOADING -> {
                    listProgressBar?.visibility = View.VISIBLE
                    zoneListRV?.visibility = View.GONE
                }
                ERROR -> {
                    listProgressBar?.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(performances: List<Performance>) {
        adapter.addData(performances)
        adapter.notifyDataSetChanged()
    }
}