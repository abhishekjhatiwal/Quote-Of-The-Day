package com.example.quoteoftheday.data

// Data class for Quote
data class Quote(
    val id: Int,
    val text: String,
    val author: String,
    var isFavorite: Boolean = false
)