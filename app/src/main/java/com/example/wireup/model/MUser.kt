package com.example.wireup.model

//data class MUser(val id: String?,
//                 val userId: String,
//                 val displayName: String){
//    fun toMap(): MutableMap<String, Any> {
//        return mutableMapOf("user_id" to this.userId,
//            "display_name" to this.displayName)
//    }
//
//}

data class MUser(
    val id: String = "",
    var name: String = "",
    var email: String = "",
    var profileImage: String = "https://static.vecteezy.com/system/resources/thumbnails/005/545/335/small/user-sign-icon-person-symbol-human-avatar-isolated-on-white-backogrund-vector.jpg",
    val followers: MutableList<String> = mutableListOf(),
    var uniqueId: String = ""
)