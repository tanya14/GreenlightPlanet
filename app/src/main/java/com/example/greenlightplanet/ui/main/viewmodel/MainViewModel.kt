package com.example.greenlightplanet.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenlightplanet.model.Performance
import com.example.greenlightplanet.repository.MainRepository
import com.example.greenlightplanet.utility.NetworkHelper
import com.example.greenlightplanet.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository?,
    private val networkHelper: NetworkHelper?
) : ViewModel() {

    private val performanceByZone = MutableLiveData<Resource<Performance>>()

    fun getPerformanceByZoneList(): LiveData<Resource<Performance>> {
        return performanceByZone
    }

    init {
        fetchPerformance()
    }

    private fun fetchPerformance() {
        viewModelScope.launch {
            performanceByZone.postValue(Resource.loading(null))
            if (networkHelper?.isNetworkConnected() == true) {
                mainRepository?.getPerformanceByZone()?.let {
                    if (it.isSuccessful) {
                        performanceByZone.postValue(Resource.success((it.body())?.responseData))
                    } else performanceByZone.postValue(
                        Resource.error(
                            it.errorBody().toString(),
                            null
                        )
                    )
                }
            } else performanceByZone.postValue(Resource.error("No internet connection", null))
        }
    }
}