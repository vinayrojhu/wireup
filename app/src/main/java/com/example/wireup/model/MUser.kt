package com.example.wireup.model

import android.net.Uri

data class MUser(
    val id: String = "",
    var name: String = "",
    var email: String = "",
    var profileImage: String = "",
    val followers: MutableList<String> = mutableListOf(),
    var uniqueId: String = ""
)

data class Follower(
    val followerId: String = "",
    val name: String = "",
    val image: Uri? = null
)