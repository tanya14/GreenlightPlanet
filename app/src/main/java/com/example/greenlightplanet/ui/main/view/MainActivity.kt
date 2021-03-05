package com.example.greenlightplanet.ui.main.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greenlightplanet.R
import com.example.greenlightplanet.model.*
import com.example.greenlightplanet.ui.main.adapter.ListAdapter
import com.example.greenlightplanet.ui.main.viewmodel.MainViewModel
import com.example.greenlightplanet.utility.Status.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: ListAdapter
    private var performances: Performance? = null
    private var zones: ArrayList<Zone>? = ArrayList()
    private var regions: ArrayList<Region>? = ArrayList()
    private var areas: ArrayList<Area>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupObserver()
        supportActionBar?.title = getString(R.string.country)
    }

    private fun setupUI() {
        zoneListRV?.layoutManager = LinearLayoutManager(this)
        adapter = ListAdapter(object : ListAdapter.OnListItemClick {
            override fun onListItemClicked(clickedItem: Any?) {
                renderListAfterItemClicked(clickedItem)
            }

        })
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
                    it.data?.let { performance ->
                        performances = performance
                        ((performance.country?.let { country ->
                            renderList(country)
                        }))
                    }
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

    private fun renderList(performances: List<Any>?) {
        performances?.let { adapter.addData(it) }
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val tempList: List<*>? = adapter.getAdapterDataList()
        if (tempList?.isNotEmpty() == true) {
            when (tempList[0]) {
                is Country -> {
                    finishAffinity()
                }
                is Zone -> {
                    renderList(performances?.country)
                    supportActionBar?.title = getString(R.string.country)
                }
                is Region -> {
                    renderList(zones)
                    supportActionBar?.title = getString(R.string.zone)
                }
                is Area -> {
                    renderList(regions)
                    supportActionBar?.title = getString(R.string.region)
                }
                is Employee -> {
                    renderList(areas)
                    supportActionBar?.title = getString(R.string.area)
                }
            }
        }
    }

    private fun renderListAfterItemClicked(clickedItem: Any?) {
        when (clickedItem) {
            is Country -> {
                val clickedTempList = arrayListOf<Zone>()
                if (performances?.zone != null) {
                    for (items in performances?.zone!!) {
                        if (clickedItem.territory?.let { items.territory?.contains(it) } == true) {
                            clickedTempList.add(items)
                        }
                    }
                }
                renderList(clickedTempList)
                zones = clickedTempList
                supportActionBar?.title = getString(R.string.zone)
            }
            is Zone -> {
                val clickedTempList = arrayListOf<Region>()
                if (performances?.zone != null) {
                    for (items in performances?.region!!) {
                        if (clickedItem.territory?.let { items.territory?.contains(it) } == true) {
                            clickedTempList.add(items)
                        }
                    }
                }
                renderList(clickedTempList)
                regions = clickedTempList
                supportActionBar?.title = getString(R.string.region)
            }
            is Region -> {
                val clickedTempList = arrayListOf<Area>()
                if (performances?.zone != null) {
                    for (items in performances?.area!!) {
                        if (clickedItem.territory?.let { items.territory?.contains(it) } == true) {
                            clickedTempList.add(items)
                        }
                    }
                }
                renderList(clickedTempList)
                areas = clickedTempList
                supportActionBar?.title = getString(R.string.area)
            }
            is Area -> {
                val clickedTempList = arrayListOf<Employee>()
                if (performances?.zone != null) {
                    for (items in performances?.employee!!) {
                        if (clickedItem.territory?.let { items.territory?.contains(it) } == true) {
                            clickedTempList.add(items)
                        }
                    }
                }
                renderList(clickedTempList)
                supportActionBar?.title = getString(R.string.employee)
            }
            is Employee -> {
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchViewItem = menu?.findItem(R.id.search_bar)
        val searchView = searchViewItem?.actionView as SearchView?

        searchView?.isIconified = true
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return true
    }
}