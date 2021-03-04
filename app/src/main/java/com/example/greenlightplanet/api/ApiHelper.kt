package com.example.greenlightplanet.api

import com.example.greenlightplanet.model.User
import retrofit2.Response

interface ApiHelper {

        suspend fun getUsers(): Response<List<User>>

}