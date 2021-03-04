package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

}