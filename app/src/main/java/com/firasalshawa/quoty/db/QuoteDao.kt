package com.firasalshawa.quoty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.firasalshawa.quoty.models.QuoteResponse
import com.firasalshawa.quoty.util.Resource

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(quote:QuoteResponse):Long

    @Query("SELECT * FROM Quotes")
    fun getAllQuotes():LiveData<List<QuoteResponse>>

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuote():QuoteResponse

    @Query("SELECT * FROM Quotes WHERE fav=1")
    fun getAllLikedQuotes(): LiveData<List<QuoteResponse>>

    @Delete
    suspend fun deleteQuote(quote:QuoteResponse)

}