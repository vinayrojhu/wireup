package com.example.wireup.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wireup.model.MUser
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
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: ""
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

    fun updateUserData(userId: String, userData: MUser): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val userRef = firestore.collection("users").document(userId)
        userRef.update("name", userData.name)
            .addOnSuccessListener {
                userRef.update("email", userData.email)
                    .addOnSuccessListener {
                        liveData.value = true
                    }
                    .addOnFailureListener { exception ->
                        Log.d("FirestoreRepository", "Error updating user email", exception)
                        liveData.value = false
                    }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error updating user name", exception)
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

}
