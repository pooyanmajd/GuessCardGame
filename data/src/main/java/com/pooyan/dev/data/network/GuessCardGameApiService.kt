package com.pooyan.dev.data.network

import retrofit2.http.GET

interface GuessCardGameApiService {

    @GET("json")
    suspend fun getCards(): List<CardDto>
}