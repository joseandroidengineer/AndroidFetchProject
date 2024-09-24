package com.jge.androidfetch.data

import com.jge.androidfetch.domain.FetchItem
import retrofit2.http.GET

interface FetchApi {

    @GET("/hiring.json")
    suspend fun getListOfIds(): List<FetchItem>
}