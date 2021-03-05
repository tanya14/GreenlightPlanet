package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.ResponseData
import retrofit2.Response
import javax.inject.Inject


class ApiHelperImpl @Inject constructor(private val apiService: ApiService?) : ApiHelper {

    override suspend fun getPerformanceByZone(): Response<ResponseData>? =
        apiService?.getPerformanceByZone()

}