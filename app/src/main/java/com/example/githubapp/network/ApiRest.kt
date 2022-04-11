package com.example.githubapp.network

import com.example.githubapp.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRest {

    @GET("search/users")
    fun getUsers(@Query("q") username: String): Call<Users>
}