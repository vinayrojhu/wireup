package com.example.wireup.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wireup.model.FlipNews
import com.example.wireup.model.MUser
import com.example.wireup.model.NewsData1
import com.example.wireup.model.SearchData
import com.example.wireup.ui.Screen.Comment
import com.example.wireup.ui.Screen.Tweet
import com.example.wireup.ui.Screen.VideoPodcast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    fun getUserData(userId: String): LiveData<MUser> {
        val liveData = MutableLiveData<MUser>()
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = MUser(
                        id = document.getString("userID") ?: "",
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        uniqueId = document.getString("uniqueId") ?: "",
                        profileImage = document.getString("profile_image") ?: ""
                    )
                    liveData.value = user
                } else {
                    Log.d("FirestoreRepository", "User not found {$userId}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting user data", exception)
            }
        return liveData
    }

    fun getUser(userId: String): Flow<MUser> = callbackFlow {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = MUser(
                        id = document.getString("userID") ?: "",
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        uniqueId = document.getString("uniqueId") ?: "",
                        profileImage = document.getString("profile_image") ?: ""
                    )
                    trySend(user)
                } else {
                    Log.d("FirestoreRepository", "User not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting user data", exception)
                close(exception)
            }
        awaitClose {}
    }.flowOn(Dispatchers.IO)

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
        firestore.collection("tweets").orderBy("timeStamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { querySnapshot ->
                val tweets = querySnapshot.documents.map { document ->
                    Tweet(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        imageUrl = document.getString("imageUrl"),
                        description = document.getString("description") ?: "",
                        likeCount = document.getLong("likeCount")?.toInt() ?: 0,
                        bookmarkCount = document.getLong("bookmarkCount")?.toInt() ?: 0,
                        retweetCount = document.getLong("retweetCount")?.toInt() ?: 0,
                        timeStamp = document.getLong("timeStamp") ?: System.currentTimeMillis()
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

    fun fetchTweetFromFirestore(tweetId: String): LiveData<Tweet?> {
        val liveData = MutableLiveData<Tweet?>()
        firestore.collection("tweets").document(tweetId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val tweet = documentSnapshot.toObject(Tweet::class.java)
                    liveData.value = tweet
                } else {
                    liveData.value = null
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting tweet", exception)
                liveData.value = null
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

    fun addFollowerToUser(userId: String, followerId: String, callback: (Boolean) -> Unit) {
        firestore.collection("users").document(userId)
            .update("followers", FieldValue.arrayUnion(followerId))
        callback(true)
    }

    suspend fun getFollowerCount(userId: String): Int {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                val followers = document.get("followers") as? List<String>
                followers?.size ?: 0
            } else {
                0
            }
        } catch (e: Exception) {
            Log.d("FirestoreRepository", "Error getting follower count: $e")
            0
        }
    }


    suspend fun getFollowersOfUser(userId: String): List<String> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                document.get("followers") as? List<String> ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.d("Error", "get failed with ", e)
            emptyList()
        }
    }

    fun fetchTweetsFromFollowedUsers(/*userId: String,*/ followedUserIds: List<String>): LiveData<List<Tweet>> {
        val liveData = MutableLiveData<List<Tweet>>()
        firestore.collection("tweets").whereIn("userId", followedUserIds).get()
            .addOnSuccessListener { querySnapshot ->
                val tweets = querySnapshot.documents.map { document ->
                    Tweet(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        imageUrl = document.getString("imageUrl"),
                        description = document.getString("description") ?: "",
                        likeCount = document.getLong("likeCount")?.toInt() ?: 0,
                        bookmarkCount = document.getLong("bookmarkCount")?.toInt() ?: 0,
                        retweetCount = document.getLong("retweetCount")?.toInt() ?: 0,
                        timeStamp = document.getLong("timeStamp") ?: System.currentTimeMillis()
                    )
                }.sortedByDescending { it.timeStamp } // Sort tweets in descending order
                liveData.value = tweets
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting tweets", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    fun addFollowingToUser(userId: String, followingId: String, callback: (Boolean) -> Unit) {
        firestore.collection("users").document(userId)
            .update("following", FieldValue.arrayUnion(followingId))
        callback(true)
    }

    suspend fun getFollowingOfUser(userId: String): List<String> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                document.get("following") as? List<String> ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.d("Error", "get failed with ", e)
            emptyList()
        }
    }

    fun fetchTweetsofCurrentUser(userId: String): LiveData<List<Tweet>> {
        val liveData = MutableLiveData<List<Tweet>>()
        firestore.collection("tweets").whereEqualTo("userId", userId).orderBy("timeStamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { querySnapshot ->
                val tweets = querySnapshot.documents.map { document ->
                    Tweet(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        imageUrl = document.getString("imageUrl"),
                        description = document.getString("description") ?: "",
                        likeCount = document.getLong("likeCount")?.toInt() ?: 0,
                        bookmarkCount = document.getLong("bookmarkCount")?.toInt() ?: 0,
                        retweetCount = document.getLong("retweetCount")?.toInt() ?: 0,
                        timeStamp = document.getLong("timeStamp") ?: System.currentTimeMillis()
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


    fun fetchNewsFromFirestore(): LiveData<List<NewsData1>> {
        val liveData = MutableLiveData<List<NewsData1>>()
        firestore.collection("news").orderBy("time", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { querySnapshot ->
                val news = querySnapshot.documents.map { document ->
                    NewsData1(
                        id = document.id,
                        description = document.getString("description") ?: "",
                        heading = document.getString("heading") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        report = document.getString("report") ?: "",
                        tags = document.get("tags") as? List<String>,
                        time = document.getLong("time") ?: 0L
                    )
                }
                liveData.value = news
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting news", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    fun getNewsData(Id: String): LiveData<NewsData1> {
        val liveData = MutableLiveData<NewsData1>()
        firestore.collection("news").document(Id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val newsread= NewsData1(
                        id = document.id,
                        description = document.getString("description") ?: "",
                        heading = document.getString("heading") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        report = document.getString("report") ?: "",
                        tags = document.get("tags") as? List<String>,
                        time = document.getLong("time") ?: 0L
                    )

                    liveData.value = newsread
                } else {
                    Log.d("FirestoreRepository", "news not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting news data", exception)
            }
        return liveData
    }

    fun fetchFlipNews(): LiveData<List<FlipNews>> {
        val liveData = MutableLiveData<List<FlipNews>>()
        firestore.collection("flipnews").orderBy("time", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { querySnapshot ->
                val flipNews = querySnapshot.documents.map { document ->
                    FlipNews(
                        id = document.id,
                        description1 = document.getString("description1") ?: "",
                        heading1 = document.getString("heading1") ?: "",
                        imageUrl1 = document.getString("imageUrl1") ?: "",
                        report1 = document.getString("report1") ?: "",
                        time = document.getLong("time") ?: 0L,
                        description2 = document.getString("description2") ?: "",
                        heading2 = document.getString("heading2") ?: "",
                        imageUrl2 = document.getString("imageUrl2") ?: "",
                        report2 = document.getString("report2") ?: "",
                    )
                }
                liveData.value = flipNews
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting news", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    suspend fun searchNews(query: String): List<SearchData> {
        Log.d("FirestoreRepository", "Searching for news with query: $query")
        val lowerCaseQuery = query.toLowerCase()
        return firestore.collection("news")
            .get()
            .await()
            .documents
            .filter { document ->
                val description = document.getString("description")?.toLowerCase() ?: ""
                val heading = document.getString("heading")?.toLowerCase() ?: ""
                val imageUrl = document.getString("imageUrl")?.toLowerCase() ?: ""
                val report = document.getString("report")?.toLowerCase() ?: ""
                val tags = document.get("tags") as? List<String>
                val tagsString = tags?.joinToString(" ")?.toLowerCase() ?: ""

                description.contains(lowerCaseQuery) ||
                        heading.split(" ").any { it.contains(lowerCaseQuery) } ||
                        imageUrl.contains(lowerCaseQuery) ||
                        report.contains(lowerCaseQuery) ||
                        tagsString.contains(lowerCaseQuery)
            }
            .map {
                SearchData(
                    id = it.id,
                    description = it.getString("description") ?: "",
                    heading = it.getString("heading") ?: "",
                    imageUrl = it.getString("imageUrl") ?: "",
                    report = it.getString("report") ?: ""
                )
            }
    }

    fun VerifyUserID(uuid: String) {
        Log.d("FirebaseRepository", "Uploading data to Firebase...")
        val data = hashMapOf(
            "uuid" to uuid
        )

        firestore.collection("PendingUsers").add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("FirebaseRepository", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FirebaseRepository", "Error adding document", e)
            }
    }

    fun fetchYTlink(): LiveData<List<VideoPodcast>> {
        val liveData = MutableLiveData<List<VideoPodcast>>()
        firestore.collection("YTvideo").orderBy("Time", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { querySnapshot ->
                val video = querySnapshot.documents.map { document ->
                    VideoPodcast(
                        id = document.id,
                        title = document.getString("heading") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        videoLink = document.getString("VideoUrl") ?: "",
                    )
                }
                liveData.value = video
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error getting news", exception)
                liveData.value = emptyList()
            }
        return liveData
    }

    fun deleteTweet(tweetId: String, imageUrl: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val tweetRef = firestore.collection("tweets").document(tweetId)
        val uri = Uri.parse(imageUrl)
        val imageName = uri.pathSegments.last()

        if (imageUrl != "") {
            val storageRef = FirebaseStorage.getInstance().reference.child(imageName)
            storageRef.delete().addOnSuccessListener {
                tweetRef.delete()
                    .addOnSuccessListener {
                        liveData.value = true
                    }
                    .addOnFailureListener { exception ->
                        Log.d("FirestoreRepository", "Error deleting tweet", exception)
                        liveData.value = false
                    }
            }.addOnFailureListener { exception ->
                Log.d("FirestoreRepository", "Error deleting image", exception)
                liveData.value = false
            }
        } else {
            tweetRef.delete()
                .addOnSuccessListener {
                    liveData.value = true
                }
                .addOnFailureListener { exception ->
                    Log.d("FirestoreRepository", "Error deleting tweet", exception)
                    liveData.value = false
                }
        }

        return liveData
    }

    fun addComment(tweetId: String, comment: Comment): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val tweetRef = firestore.collection("tweets").document(tweetId)
        tweetRef.update("comments", FieldValue.arrayUnion(comment))
            .addOnSuccessListener {
                liveData.value = true
            }
            .addOnFailureListener { exception ->
                Log.d("CommentRepository", "Error adding comment", exception)
                liveData.value = false
            }
        return liveData
    }

    fun getComments(tweetId: String): LiveData<List<Comment>> {
        val liveData = MutableLiveData<List<Comment>>()
        val tweetRef = firestore.collection("tweets").document(tweetId)

        tweetRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    val commentsData = document.get("comments") as? List<Map<String, Any>>
                    val commentList = commentsData?.map { commentMap ->
                        Comment(
                            id = commentMap["id"] as? String ?: "",
                            description = commentMap["description"] as? String ?: "",
                            userId = commentMap["userId"] as? String ?: "",
                            imageUrl = commentMap["imageUrl"] as? String,
                            likeCount = (commentMap["likeCount"] as? Long)?.toInt() ?: 0,
                            likes = (commentMap["likes"] as? List<String>)?.toMutableList() ?: mutableListOf(),
                            timeStamp = (commentMap["timeStamp"] as? Long) ?: 0L
                        )
                    } ?: emptyList()
                    liveData.value = commentList
                } else {
                    liveData.value = emptyList()
                }
            } else {
                liveData.value = emptyList()
            }
        }
        return liveData
    }

    fun likeComment(tweetId: String, commentId: String, userId: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val tweetRef = firestore.collection("tweets").document(tweetId)

        Log.d("CommentRepository", "Attempting to like comment with id: $commentId by user: $userId")

        // Retrieve the tweet document
        tweetRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val comments = document.get("comments") as? List<Map<String, Any>> ?: emptyList()
                val commentToUpdate = comments.find { it["id"] == commentId }

                if (commentToUpdate != null) {
                    Log.d("CommentRepository", "Comment found: $commentToUpdate")

                    // Check if the user has already liked the comment
                    val likes = commentToUpdate["likes"] as? List<String> ?: emptyList()
                    if (!likes.contains(userId)) {
                        Log.d("CommentRepository", "User  has not liked the comment yet. Liking now...")

                        // Update the likes array and increment the like count
                        val updatedLikes = likes + userId
                        val updatedLikeCount = (commentToUpdate["likeCount"] as? Long ?: 0) + 1

                        // Create a new comments array with the updated comment
                        val updatedComments = comments.map { comment ->
                            if (comment["id"] == commentId) {
                                comment.toMutableMap().apply {
                                    put("likes", updatedLikes)
                                    put("likeCount", updatedLikeCount)
                                }
                            } else {
                                comment
                            }
                        }

                        // Update the tweet document with the new comments array
                        tweetRef.update("comments", updatedComments)
                            .addOnSuccessListener {
                                Log.d("CommentRepository", "Successfully liked the comment.")
                                liveData.postValue(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.d("CommentRepository", "Error liking comment", exception)
                                liveData.postValue(false)
                            }
                    } else {
                        Log.d("CommentRepository", "User  has already liked the comment.")
                        liveData.postValue(false)
                    }
                } else {
                    Log.d("CommentRepository", "Comment does not exist.")
                    liveData.postValue(false)
                }
            } else {
                Log.d("CommentRepository", "Tweet does not exist.")
                liveData.postValue(false)
            }
        }.addOnFailureListener { exception ->
            Log.d("CommentRepository", "Error finding tweet", exception)
            liveData.postValue(false)
        }

        return liveData
    }

    fun hasComments(tweetId: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val tweetRef = firestore.collection("tweets").document(tweetId)

        tweetRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    // Check if the comments field exists and is not empty
                    val commentsData = document.get("comments") as? List<*>
                    liveData.value = commentsData != null && commentsData.isNotEmpty()
                } else {
                    liveData.value = false
                }
            } else {
                liveData.value = false
            }
        }
        return liveData
    }
}
