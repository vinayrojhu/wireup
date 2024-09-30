package com.example.wireup.model

import com.google.errorprone.annotations.Keep

data class NewsData1(
    val id: String,
    val description: String,
    val heading: String,
    val imageUrl: String,
    val report: String,
    val tags: List<String>? = mutableListOf(),
    val time: Long
)

data class FlipNews(
    val id: String,
    val description1: String,
    val heading1: String,
    val imageUrl1: String,
    val report1: String,
    val time: Long,
    val description2: String,
    val heading2: String,
    val imageUrl2: String,
    val report2: String
)

@Keep
data class SearchData(
    val id: String,
    val description: String,
    val heading: String,
    val imageUrl: String,
    val report: String
) {
    constructor() : this("", "","","","") // Add this no-argument constructor
}