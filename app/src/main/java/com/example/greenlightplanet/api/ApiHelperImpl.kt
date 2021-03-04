package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.Performance
import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPerformanceByZone(): Response<List<Performance>> = apiService.getPerformanceByZone()

}