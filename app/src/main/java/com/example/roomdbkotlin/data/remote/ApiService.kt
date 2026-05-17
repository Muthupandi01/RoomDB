package com.example.roomdbkotlin.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}