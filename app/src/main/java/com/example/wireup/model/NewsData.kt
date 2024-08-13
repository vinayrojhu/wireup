package com.example.wireup.model

data class NewsData(
    val category: String,
    val trending: Boolean,
    val imageurl: String,
    val date: String,
    val title: String,
    val subtitle: String
)