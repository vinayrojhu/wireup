package com.example.wireup.model

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MUser(
    val id: String = "",
    var name: String = "",
    var email: String = "",
    var profileImage: String = "",
    val followers: MutableList<String> = mutableListOf(),
    var uniqueId: String = ""
)

//data class Follower(val name: String, val image: String)

data class Follower(
    val followerId: String = "",
    val name: String = "",
    val image: Uri? = null
    // Add other relevant fields as needed
)