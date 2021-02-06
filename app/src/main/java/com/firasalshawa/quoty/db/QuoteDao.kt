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

    @Delete
    suspend fun deleteQuote(quote:QuoteResponse)

}