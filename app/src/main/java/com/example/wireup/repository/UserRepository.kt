package com.example.wireup.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wireup.model.MUser
import com.example.wireup.ui.Screen.Tweet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    fun getUserData(userId: String): LiveData<MUser> {
        val liveData = MutableLiveData<MUser>()
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = MUser(
                        id = document.getString("id") ?: "",
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        uniqueId = document.getString("uniqueId") ?: ""
                    )
                    liveData.value = user
                } else {
                    Log.d("FirestoreRepository", "User not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting user data", exception)
            }
        return liveData
    }

//    fun updateUserData(userId: String, userData: MUser): LiveData<Boolean> {
//        val liveData = MutableLiveData<Boolean>()
//        val userRef = firestore.collection("users").document(userId)
//        userRef.update("name", userData.name)
//            .addOnSuccessListener {
//                userRef.update("email", userData.email)
//                    .addOnSuccessListener {
//                        liveData.value = true
//                    }
//                    .addOnFailureListener { exception ->
//                        Log.d("FirestoreRepository", "Error updating user email", exception)
//                        liveData.value = false
//                    }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("FirestoreRepository", "Error updating user name", exception)
//                liveData.value = false
//            }
//        return liveData
//    }

    fun updateUserData(userId: String, userData: MUser): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val userRef = firestore.collection("users").document(userId)
        val updates = hashMapOf<String, Any>(
            "name" to userData.name,
            "email" to userData.email,
            "uniqueId" to userData.uniqueId
        )
        userRef.update(updates)
            .addOnSuccessListener {
                liveData.value = true
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error updating user data", exception)
                liveData.value = false
            }
        return liveData
    }

    fun uploadImageToFirebaseStorage(imageUri: Uri, userId: String): LiveData<String> {

        val liveData = MutableLiveData<String>()
        val imageRef = storage.reference.child("users/$userId/profile_image")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    liveData.value = uri.toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ImageRepository", "Error uploading image", exception)
                liveData.value = ""
            }

        return liveData
    }

    fun addTweet(tweet: Tweet): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val tweetRef = firestore.collection("tweets").document()
        tweetRef.set(tweet)
            .addOnSuccessListener {
                liveData.value = true
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error adding tweet", exception)
                liveData.value = false
            }
        return liveData
    }

    fun fetchTweetsFromFirestore(): LiveData<List<Tweet>> {
        val liveData = MutableLiveData<List<Tweet>>()
        firestore.collection("tweets").get()
            .addOnSuccessListener { querySnapshot ->
                val tweets = querySnapshot.documents.map { document ->
                    Tweet(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        imageUrl = document.getString("imageUrl"),
                        description = document.getString("description") ?: "",
                        likeCount = document.getLong("likeCount")?.toInt() ?: 0,
                        bookmarkCount = document.getLong("bookmarkCount")?.toInt() ?: 0,
                        retweetCount = document.getLong("retweetCount")?.toInt() ?: 0
                    )
                }
                liveData.value = tweets
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting tweets", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    fun fetchUsersFromFirestore(): LiveData<List<MUser>> {
        val liveData = MutableLiveData<List<MUser>>()
        firestore.collection("users").get()
            .addOnSuccessListener { querySnapshot ->
                val users = querySnapshot.documents.map { document ->
                    MUser(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        profileImage = document.getString("profile_image") ?: ""
                    )
                }
                liveData.value = users
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting users", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    fun followUser(currentUserId: String, userId: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val userRef = firestore.collection("users").document(userId)
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(MUser::class.java)
                    if (user != null) {
                        if (!user.followers.contains(currentUserId)) {
                            user.followers.add(currentUserId)
                            userRef.set(user)
                                .addOnSuccessListener {
                                    liveData.value = true
                                }
                                .addOnFailureListener { exception ->
                                    Log.d("FirestoreRepository", "Error following user", exception)
                                    liveData.value = false
                                }
                        } else {
                            liveData.value = true // already following
                        }
                    } else {
                        Log.d("FirestoreRepository", "User not found")
                        liveData.value = false
                    }
                } else {
                    Log.d("FirestoreRepository", "User not found")
                    liveData.value = false
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting user", exception)
                liveData.value = false
            }
        return liveData
    }

    fun getFollowers(userId: String): LiveData<List<String>> {
        val liveData = MutableLiveData<List<String>>()
        val userRef = firestore.collection("users").document(userId)
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(MUser::class.java)
                    if (user != null) {
                        liveData.value = user.followers ?: emptyList()
                    } else {
                        liveData.value = emptyList()
                    }
                } else {
                    liveData.value = emptyList()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting followers", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    fun checkUniqueIdAvailability(uniqueId: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        firestore.collection("users")
            .whereEqualTo("uniqueId", uniqueId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                liveData.value = querySnapshot.documents.isEmpty()
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error checking unique ID", exception)
                liveData.value = false
            }
        return liveData
    }

}



