package com.example.greenlightplanet.ui.main.view

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
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
import com.example.greenlightplanet.utility.Resource
import com.example.greenlightplanet.utility.Status.*
import com.google.gson.Gson
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
    private var searchViewItem: MenuItem? = null
    private var doubleBackToExitPressedOnce = false
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        sharedPref = getSharedPreferences(SHARED_KEY, MODE_PRIVATE)
        supportActionBar?.title = getString(R.string.country)
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
            val performanceObj = it
            if (performanceObj.data == null) {
                performanceObj.data = getOfflineData() as Performance?
                if (performanceObj.data != null) {
                    performanceObj.status = SUCCESS
                    Toast.makeText(this, performanceObj.message, Toast.LENGTH_LONG).show()
                }
            }
            when (performanceObj.status) {
                SUCCESS -> {
                    listProgressBar?.visibility = View.GONE
                    performanceObj.data?.let { performance ->
                        performances = performance
                        ((performance.country?.let { country ->
                            renderList(country)
                        }))
                    }
                    zoneListRV?.visibility = View.VISIBLE
                    saveDataForOfflineMode(performanceObj.data)
                }
                LOADING -> {
                    listProgressBar?.visibility = View.VISIBLE
                    zoneListRV?.visibility = View.GONE
                }
                ERROR -> {
                    listProgressBar?.visibility = View.GONE
                    Toast.makeText(this, performanceObj.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(performances: List<Any>?) {
        performances?.let { adapter.addData(it.toMutableList()) }
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        when (adapter.getAdapterItem()) {
            is Country -> {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }
                this.doubleBackToExitPressedOnce = true
                Toast.makeText(this, getString(R.string.backPressAgainMsg), Toast.LENGTH_SHORT)
                    .show()

                Handler(Looper.getMainLooper()).postDelayed(
                    { doubleBackToExitPressedOnce = false },
                    2000
                )
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
        searchViewItem?.isVisible = adapter.getAdapterItem() is Employee
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
        }
        searchViewItem?.isVisible = adapter.getAdapterItem() is Employee
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        searchViewItem = menu?.findItem(R.id.search_bar)
        val searchView = searchViewItem?.actionView as SearchView?
        searchViewItem?.isVisible = adapter.getAdapterItem() is Employee

        searchView?.isIconified = true
        searchView?.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        return true
    }

    private fun saveDataForOfflineMode(objectToSave: Performance?) {
        val prefsEditor: SharedPreferences.Editor? = sharedPref?.edit()
        val gson = Gson()
        val json = gson.toJson(objectToSave)
        prefsEditor?.putString(SHARED_KEY_OBJECT, json)
        prefsEditor?.apply()
    }

    private fun getOfflineData(): Any? {
        val gson = Gson()
        val json: String? = sharedPref?.getString(SHARED_KEY_OBJECT, null)
        return gson.fromJson(json, Performance::class.java)
    }

    companion object {
        private const val SHARED_KEY = "SHARED_KEY"
        private const val SHARED_KEY_OBJECT = "SHARED_KEY_OBJECT"
    }
}