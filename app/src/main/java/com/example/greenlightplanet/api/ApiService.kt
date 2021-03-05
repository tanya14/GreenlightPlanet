package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/assignment")
    suspend fun getPerformanceByZone(): Response<ResponseData>

}