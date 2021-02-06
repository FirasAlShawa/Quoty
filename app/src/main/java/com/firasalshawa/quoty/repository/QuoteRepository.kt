package com.firasalshawa.quoty.repository

import com.firasalshawa.quoty.api.RetrofitInstance

// TODO: 2/6/2021 Add QuoteDatabase in the constructor
class QuoteRepository {

    suspend fun getQuote() = RetrofitInstance.api.getRandomQuote()

}