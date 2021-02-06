package com.firasalshawa.quoty.models

data class QuoteResponse(
    val author: String,
    val id: Int,
    val permalink: String,
    val quote: String
)