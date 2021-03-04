package com.example.greenlightplanet.repository

import com.example.greenlightplanet.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getPerformanceByZone() =  apiHelper.getPerformanceByZone()

}
