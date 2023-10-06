package com.example.pxabay_api_project

data class QuoteList(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)