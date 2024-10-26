package com.example.wireup.ui.Screen.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.wireup.model.FlipNews
import com.example.wireup.model.MUser
import com.example.wireup.model.NewsData1
import com.example.wireup.model.SearchData
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.Comment
import com.example.wireup.ui.Screen.Tweet
import com.example.wireup.ui.Screen.VideoPodcast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


class UserViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets: LiveData<List<Tweet>> = _tweets

    private val _tweet = MutableLiveData<Tweet?>()
    val tweet: LiveData<Tweet?> = _tweet

    private val _deleteTweetStatus = MutableLiveData<Boolean>()
    val deleteTweetStatus: LiveData<Boolean> = _deleteTweetStatus

    private val _news1 = MutableLiveData<List<NewsData1>>()
    val news1: LiveData<List<NewsData1>> = _news1

    private val _video = MutableLiveData<List<VideoPodcast>>()
    val video: LiveData<List<VideoPodcast>> = _video

    private val _flipNews = MutableLiveData<List<FlipNews>>()
    val flipNews: LiveData<List<FlipNews>> = _flipNews

    private val _searchResults = MutableLiveData<List<SearchData>>()
    val searchResults: LiveData<List<SearchData>> = _searchResults

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

    private val _comment = MutableStateFlow<List<Comment>>(emptyList())
    val comment: StateFlow<List<Comment>> = _comment

    private val _likeCommentResult = MutableLiveData<Boolean>()
    val likeCommentResult: LiveData<Boolean> get() = _likeCommentResult

    private val _commentsMap = mutableMapOf<String, MutableStateFlow<List<Comment>>>()

    fun likeComment(tweetId: String, commentId: String, userId: String) {
        firestoreRepository.likeComment(tweetId, commentId, userId).observeForever { result ->
            _likeCommentResult.value = result
        }
    }
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

    fun deleteTweet(tweetId: String, imageUri: String) {
        firestoreRepository.deleteTweet(tweetId, imageUri).observeForever { isSuccess ->
            _deleteTweetStatus.value = isSuccess
        }
    }

    fun getComments(tweetId: String){
        firestoreRepository.getComments(tweetId).observeForever { comments ->
            Log.d("CommentViewmodel", "Received comments: $comments")
            _comment.value = comments // Update the liveData value
        }
    }
    fun getComment(tweetId: String){
        val commentsFlow = _commentsMap.getOrPut(tweetId) { MutableStateFlow(emptyList()) }
        firestoreRepository.getComments(tweetId).observeForever { comments ->
            Log.d("CommentV", "Received comments: $comments")
            commentsFlow.value = comments
        }
    }
    fun getCommentsFlow(tweetId: String): StateFlow<List<Comment>> {
        return _commentsMap[tweetId] ?: MutableStateFlow(emptyList())
    }


    fun fetchTweet(tweetId: String) {
        firestoreRepository.fetchTweetFromFirestore(tweetId).observeForever { tweet ->
            _tweet.value = tweet
        }
    }
    init {
        fetchTweetsFromFirestore()
        fetchUsersFromFirestore()
        fetchNewsFromFirestore()
        fetchFlipNews()
        fetchYTlink()
    }

    private fun fetchYTlink() {
        firestoreRepository.fetchYTlink().observeForever { video ->
            _video.value = video
        }
    }

    private fun fetchNewsFromFirestore() {
        firestoreRepository.fetchNewsFromFirestore().observeForever { news ->
            _news1.value = news
        }
    }

    fun getNewsData(id: String): LiveData<NewsData1> {
        return firestoreRepository.getNewsData(id)
    }

    private fun fetchFlipNews() {
        firestoreRepository.fetchFlipNews().observeForever { news ->
            _flipNews.value = news
        }
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

        }
    }

    suspend fun getFollowingsOfUser2(userId: String): List<String> {
        return withContext(Dispatchers.IO) {
            firestoreRepository.getFollowingOfUser(userId)
        }
    }

    fun fetchTweetsofCurrentUser(userId: String) {
        firestoreRepository.fetchTweetsofCurrentUser(userId).observeForever { tweets ->
            _tweets.value = tweets
        }
    }

    fun searchNews(query: String) {
        Log.d("UserViewModel", "Searching for news with query: $query")
        viewModelScope.launch {
            val results = firestoreRepository.searchNews(query)
            Log.d("UserViewModel", "Search results: $results")
            _searchResults.value = results
        }
    }

    fun verifyUserID(uuid: String, callback: () -> Unit) {
        viewModelScope.launch {
            firestoreRepository.VerifyUserID(uuid)
        }
        callback()
    }

    fun addComment(tweetId: String, comment: Comment): LiveData<Boolean> {
        return firestoreRepository.addComment(tweetId, comment)
    }

    fun checkIfTweetHasComments(tweetId: String): LiveData<Boolean> {
        return firestoreRepository.hasComments(tweetId)
    }


}

