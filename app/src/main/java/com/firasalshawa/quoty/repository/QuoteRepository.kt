package com.firasalshawa.quoty.repository

import androidx.lifecycle.LiveData
import androidx.room.Database
import com.firasalshawa.quoty.api.RetrofitInstance
import com.firasalshawa.quoty.db.QuoteDatabase
import com.firasalshawa.quoty.models.QuoteResponse

// TODO: 2/6/2021 Add QuoteDatabase in the constructor
class QuoteRepository(
    val db:QuoteDatabase
){

    suspend fun getQuote() = RetrofitInstance.api.getRandomQuote()

    suspend fun insertQuote(quote:QuoteResponse) = db.getQuoteDao().insertQuote(quote)

    suspend fun updateQuote(quote: QuoteResponse) = db.getQuoteDao().updateQuote(quote)

    suspend fun deleteQuote(quote: QuoteResponse) = db.getQuoteDao().deleteQuote(quote)

    fun getAllQuotes() = db.getQuoteDao().getAllQuotes()

    fun getAllLikedQuotes() = db.getQuoteDao().getAllLikedQuotes()

    fun getRandomQuoteDB() :QuoteResponse = db.getQuoteDao().getRandomQuote()
}