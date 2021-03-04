package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.Performance
import com.example.greenlightplanet.model.Zone
import retrofit2.Response

interface ApiHelper {

        suspend fun getPerformanceByZone(): Response<List<Performance>>

}