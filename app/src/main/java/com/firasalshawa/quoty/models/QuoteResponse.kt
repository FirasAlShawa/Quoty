package com.firasalshawa.quoty.models

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Quotes"
)
data class QuoteResponse(
    val author: String,
    @PrimaryKey
    val id: Int,
    val permalink: String= " ",
    val quote: String,
    var fav:Boolean = false
)