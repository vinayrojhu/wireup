package com.example.wireup.model

import androidx.compose.ui.graphics.painter.Painter


data class FFNewsItem(
    val heading: String,
    val description: String,
    val image: Painter
)

data class FFData(
    val item1: FFNewsItem,
    val item2: FFNewsItem
)