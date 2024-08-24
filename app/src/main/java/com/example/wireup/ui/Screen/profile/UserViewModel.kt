package com.example.wireup.ui.Screen.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class UserViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> = _tweets

    private val _users = MutableLiveData<List<MUser>>()
    val users: LiveData<List<MUser>> = _users

    private val _followers = MutableLiveData<List<String>>()
    val followers: LiveData<List<String>> = _followers

    fun getUserData(): LiveData<MUser> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.getUserData(uuid)
    }
    fun getUserData2(uuid: String): LiveData<MUser> {
        return firestoreRepository.getUserData(uuid)
    }

    fun updateUserData(userData: MUser): LiveData<Boolean> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.updateUserData(uuid, userData)
    }

    fun uploadImageToFirebaseStorage(imageUri: Uri): LiveData<String> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.uploadImageToFirebaseStorage(imageUri, userId)
    }

    fun updateUserProfileImage(imageUrl: String): LiveData<Boolean> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val liveData = MutableLiveData<Boolean>()
        getUserData().observeForever { userData ->
            userData?.profileImage = imageUrl
            updateUserData(userData).observeForever { isSuccess ->
                liveData.value = isSuccess
            }
        }
        return liveData
    }

//    fun addTweet(tweetText: String, imageUrl: String) {
//        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
//        val tweet = Tweet(userId = userId, description = tweetText, NodeimageUrl = imageUrl )
//        firestoreRepository.addTweet(tweet)
//    }

    init {
        fetchTweetsFromFirestore()
        fetchUsersFromFirestore()
    }

    private fun fetchTweetsFromFirestore() {
        firestoreRepository.fetchTweetsFromFirestore().observeForever { tweets ->
            _tweets.value = tweets
        }
    }

    private fun fetchUsersFromFirestore() {
        firestoreRepository.fetchUsersFromFirestore().observeForever { users ->
            _users.value = users
        }
    }

    fun addTweet(tweetText: String, imageUri: Uri? = null) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        if (imageUri != null) {
            uploadImageToStorage(imageUri).observeForever { nodeimageUrl ->
                if (nodeimageUrl.isNotEmpty()) {
                    val tweet = Tweet(userId = userId, description = tweetText, imageUrl = nodeimageUrl)
                    firestoreRepository.addTweet(tweet).observeForever { isSuccess ->
                        if (isSuccess) {
                            fetchTweetsFromFirestore() // Update tweets state
                        }
                    }
                } else {
                    Log.d("ViewModel", "Error uploading image")
                }
            }
        } else {
            val tweet = Tweet(userId = userId, description = tweetText, imageUrl = null)
            firestoreRepository.addTweet(tweet).observeForever { isSuccess ->
                if (isSuccess) {
                    fetchTweetsFromFirestore() // Update tweets state
                }
            }
        }
    }

    private fun uploadImageToStorage(imageUri: Uri): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    liveData.value = uri.toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("ViewModel", "Error uploading image", exception)
                liveData.value = ""
            }
        return liveData
    }

    fun followUser(userId: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        firestoreRepository.followUser(currentUserId, userId).observeForever { isSuccess ->
            if (isSuccess) {
                val currentFollowers = _followers.value
                if (currentFollowers != null) {
                    if (currentFollowers.contains(userId)) {
                        // User is already following, so remove them from the followers list
                        _followers.value = currentFollowers.minus(userId)
                        // Update the button text and icon to "Follow"
                    } else {
                        // User is not following, so add them to the followers list
                        _followers.value = currentFollowers.plus(userId)
                        // Update the button text and icon to "Unfollow"
                    }
                }
            }
        }
    }

    fun getFollowers(userId: String): LiveData<List<String>> {
        return firestoreRepository.getFollowers(userId)
    }


}

