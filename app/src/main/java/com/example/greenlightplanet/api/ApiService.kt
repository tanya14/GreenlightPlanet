package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.Performance
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET()
    suspend fun getPerformanceByZone(): Response<List<Performance>>

}