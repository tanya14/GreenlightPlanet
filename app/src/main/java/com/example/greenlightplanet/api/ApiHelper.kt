package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.ResponseData
import retrofit2.Response

interface ApiHelper {

    suspend fun getPerformanceByZone(): Response<ResponseData>?

}