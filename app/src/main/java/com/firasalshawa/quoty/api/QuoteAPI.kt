package com.firasalshawa.quoty.api

import com.firasalshawa.quoty.models.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuoteAPI {

    @GET("/")
    suspend fun getRandomQuote():Response<QuoteResponse>

}