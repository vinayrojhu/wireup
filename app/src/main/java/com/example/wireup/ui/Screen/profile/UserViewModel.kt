package com.example.wireup.ui.Screen.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wireup.model.Follower
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


class UserViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> = _tweets

    private val _users = MutableLiveData<List<MUser>>()
    val users: LiveData<List<MUser>> = _users

    private val _isUniqueIdAvailable = MutableLiveData<Boolean>()
    val isUniqueIdAvailable: LiveData<Boolean> = _isUniqueIdAvailable

    private val _followerCount = MutableStateFlow(0)
    val followerCount: StateFlow<Int> = _followerCount

    private val _followerss = MutableStateFlow<List<String>>(emptyList())
    val followerss: StateFlow<List<String>> = _followerss

    private val _following = MutableStateFlow<List<String>>(emptyList())
    val following: StateFlow<List<String>> = _following


    fun getUserData(): LiveData<MUser> {
        val uuid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        return firestoreRepository.getUserData(uuid)
    }
    fun getUserData2(uuid: String): LiveData<MUser> {
        return firestoreRepository.getUserData(uuid)
    }

    fun getUser(userId: String): Flow<MUser> = flow {
        firestoreRepository.getUser(userId).collect { user ->
            emit(user)
        }
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

    fun checkUniqueIdAvailability(uniqueId: String) {
        firestoreRepository.checkUniqueIdAvailability(uniqueId).observeForever { available ->
            _isUniqueIdAvailable.value = available
        }
    }

    fun addFollowerToUser(userId: String, followerId: String, /*followerName: String, followerImage: String*/) {
        firestoreRepository.addFollowerToUser(userId, followerId) { success ->
            _followerCount.value = (_followerCount.value ?: 0) + 1
        }
    }

    fun getFollowerCount(userId: String) {
        viewModelScope.launch {
            val count = firestoreRepository.getFollowerCount(userId)
            _followerCount.value = count
        }
    }

    fun getFollowersOfUser(userId: String) {
        viewModelScope.launch {
            val result = firestoreRepository.getFollowersOfUser(userId)
            _followerss.value = result
        }
    }

    fun fetchTweetsForCurrentUser(userId: String) {
        viewModelScope.launch {
            val followers = getFollowingsOfUser2(userId)
            if (followers.isNotEmpty()) {
                firestoreRepository.fetchTweetsFromFollowedUsers(followers).observeForever { tweets ->
                    _tweets.value = tweets
                }
            }
        }
    }

    fun addFollowingToUser(userId: String, followingId: String, /*followerName: String, followerImage: String*/) {
        firestoreRepository.addFollowingToUser(userId, followingId) { success ->
            _followerCount.value = (_followerCount.value ?: 0) + 1
        }
    }

    suspend fun getFollowingsOfUser2(userId: String): List<String> {
        return withContext(Dispatchers.IO) {
            firestoreRepository.getFollowingOfUser(userId)
        }
    }




}

