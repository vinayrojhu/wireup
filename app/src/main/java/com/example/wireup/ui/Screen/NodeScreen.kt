package com.example.wireup.ui.Screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.BackCardContent
import com.example.wireup.ui.Components.CircularImage
import com.example.wireup.ui.Components.FrontCardContent
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.example.wireup.util.DateUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


data class Tweet(
    val id: String = UUID.randomUUID().toString(),
    val description: String ="",
    val userId: String ="",
    val comments: MutableList<Comment> = mutableListOf(),
    val likeCount: Int = 0,
    val retweetCount: Int = 0,
    val bookmarkCount: Int = 0,
    val imageUrl: String? = null,
    val timeStamp: Long = System.currentTimeMillis()
)


data class Comment(
    val id: String = UUID.randomUUID().toString(),
    val description: String="",
    val userId: String="",
    val imageUrl: String? = null,
    val likeCount: Int = 0,
    val likes: MutableList<String> = mutableListOf(),
    val timeStamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    val tweets by viewModel.tweets.observeAsState(initial = emptyList())
    val users by viewModel.users.observeAsState(initial = emptyList())
    val userId = Firebase.auth.currentUser?.uid.toString()

    val isLoading by remember { mutableStateOf(false) }
    val tweetText = remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }      //
    val imageUrl = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    val showDialog2 = remember { mutableStateOf(true) }
    val showVerificationDialog = remember { mutableStateOf(false) }
    val isUserInListState = remember { mutableStateOf(false) }



    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri = data?.data
            imageUri.value = uri
        }
    }

    fun isUserInList(userId: String): Boolean {
        val db = Firebase.firestore
        return runBlocking {
            val query = db.collection("VerifiedUsers").whereEqualTo("uuid", userId).get().await()
            query.documents.isNotEmpty()
        }
    }



    LaunchedEffect(Unit) {
        isUserInListState.value = isUserInList(userId ?: "")
    }

    Box{
        Column {
            TopAppBar(title = {
                Text(
                    "Nodes", fontSize = 18.sp
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Back",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            },
                actions = {
                    Row {
                        IconButton(onClick = { navController.navigate(NavigationItem.FollowNodes.route) }) {
                            Icon(painter = painterResource(id = R.drawable.global_network),
                                contentDescription = "globe",
                                modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            )
            Divider()

            Spacer(modifier = Modifier.height(2.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (showDialog.value) {
                    AlertDialog(

                        onDismissRequest = { showDialog.value = false },
                        text = {
                            Column {
                                Text(text = "Create Node" , textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(10.dp))
                                TextField(
                                    value = tweetText.value,
                                    onValueChange = { tweetText.value = it },
                                    label = { Text("Node text") }
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                if (imageUri.value == null) {
                                    Button(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                            launcher.launch(intent)
                                        },
                                        colors = ButtonColors(containerColor = Color.LightGray , disabledContainerColor = Color.LightGray , contentColor = Black , disabledContentColor = Black),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Upload Image")
                                    }
                                } else {
                                    Image(
                                        painter = rememberImagePainter(imageUri.value),
                                        contentDescription = "Uploaded Image"
                                    )
                                }

                            }
                        },
                        buttons = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 25.dp, bottom = 10.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.addTweet(tweetText.value, imageUri.value)
                                        showDialog.value = false
                                        tweetText.value = ""
                                        imageUrl.value = ""
                                    } ,
                                    colors = ButtonColors(containerColor = Black , disabledContainerColor = Black , contentColor = Color.White , disabledContentColor = Color.White),
                                ) {
                                    Text("Done")
                                }
                            }
                        }
                    )
                }

                if (showVerificationDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showVerificationDialog.value = false },
                        text = {
                            Text("You need to apply for verification to create a node.")
                        },
                        buttons = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 25.dp, bottom = 10.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.verifyUserID(userId){
                                            showDialog2.value = false
                                        }
//                                        showVerificationDialog.value = false
                                    },
                                    enabled = showDialog2.value,
                                    colors = ButtonColors(
                                        containerColor = Black,
                                        disabledContainerColor = Black,
                                        contentColor = Color.White,
                                        disabledContentColor = Color.White
                                    ),
                                ) {
                                    Text(if (showDialog2.value) "Apply for verification" else "Applied")
                                }
                            }
                        })

                }

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {

                    tweets.take(100).forEach { tweet ->
                        val user = users.find { it.id == tweet.userId }
                        MainNode(tweet, user, navController)
                    }

                }

            }
        }
        FloatingActionButton(
            onClick = { if (isUserInListState.value) {
                showDialog.value = true
            } else {
                showVerificationDialog.value = true
            } },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(1.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Filled.Add, "Add button")
        }
    }



}


@Composable
fun MainNode(tweet: Tweet, user: MUser?, navController : NavHostController ){
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val context = LocalContext.current
    val userimage = remember { mutableStateOf<Uri?>(null) }
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
//    LaunchedEffect(Unit) {
//        FirebaseStorage.getInstance().reference.child("users/${user?.id}/profile_image").downloadUrl.addOnSuccessListener { uri ->
//            userimage.value = uri
//        }
//    }


    // Helper function to await metadata fetch
    suspend fun awaitMetadata(storageRef: StorageReference): StorageMetadata {
        return suspendCancellableCoroutine { continuation ->
            storageRef.metadata.addOnSuccessListener { metadata ->
                continuation.resume(metadata) // Resume with metadata on success
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception) // Resume with exception on failure
            }
        }
    }

    // Helper function to await download URL fetch
    suspend fun awaitDownloadUrl(storageRef: StorageReference): Uri {
        return suspendCancellableCoroutine { continuation ->
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                continuation.resume(uri) // Resume with URI on success
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception) // Resume with exception on failure
            }
        }
    }

    suspend fun fetchImageWithRetry(userId: String?) {
        val storageRef = FirebaseStorage.getInstance().reference.child("users/$userId/profile_image")

        // Attempt to fetch metadata with a retry mechanism
        val maxRetries = 3 // Set to 1 for one retry
        var attempt = 0

        while (attempt <= maxRetries) {
            try {
                // Fetch metadata
                val metadata = awaitMetadata(storageRef)

                // If metadata fetch is successful, proceed to download the URL
                val uri = awaitDownloadUrl(storageRef)
                userimage.value = uri
                return // Exit the function after success
            } catch (exception: Exception) {
                Log.e("ImageFetchError", "Attempt ${attempt + 1} failed: ${exception.message}")

                // If it's the last attempt, log the error and exit
                if (attempt == maxRetries) {
                    Log.e("ImageFetchError", "Image does not exist after retries: ${exception.message}")
                    return
                }
                // Increment attempt counter
                attempt++
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchImageWithRetry(user?.id)
    }



    val nodeimage = tweet.imageUrl
    val username = user?.name ?: ""
    val userUuid = user?.id
    val tweetId = tweet.id
    val showMostLikedComment = remember { mutableStateOf(false) }

    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    @Composable
    fun getMostLikedComment(tweetId: String): Comment? {
        val comments by viewModel.getCommentsFlow(tweetId).collectAsState(initial = emptyList())

        // Log fetching comments
        LaunchedEffect(tweetId) {
            viewModel.getComment(tweetId)
            Log.d("CommentFetch", "Fetching comments for tweetId: $tweetId")
        }

        // Check if the comments list is empty
        if (comments.isEmpty()) {
            println("No comments available for tweetId: $tweetId")
            return null // You could also return a default Comment instance if preferred
        }

        // Log the fetched comments
        Log.d("TweetComments", "Tweet ID: ${tweetId}, Comments: ${comments}")

        // Find the comment with the maximum like count
        return comments.maxByOrNull { it.likeCount }
    }
    val mostLikedComment = getMostLikedComment(tweet.id)

    val hasComments by viewModel.checkIfTweetHasComments(tweetId).observeAsState(initial = false)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.50f)
            .graphicsLayer {
                this.rotationY = rotationY
                cameraDistance = 12f * density
            },
        contentAlignment = Alignment.Center
    ) {
        if (rotationY <= 90f) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(NavigationItem.Comment.route + "/$tweetId") }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, top = 2.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularImage(
                        imageUri = userimage, imageSize = 38.dp,
                        navController = navController,
                        userUuid = userUuid.toString()
                    )
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(username, fontWeight = FontWeight.W600, fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    var showDialog by remember { mutableStateOf(false) }
                    Column {
                        if (currentUserId == tweet.userId) {
                            Column(modifier = Modifier
                                .clickable { showDialog = true }) {

                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier.padding(start = 20.dp)
                                )

                                when (val status = viewModel.deleteTweetStatus.value) {
                                    true -> {
                                        Toast.makeText(
                                            context,
                                            "Tweet deleted successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    false -> {
                                        Toast.makeText(context, "Error deleting tweet", Toast.LENGTH_SHORT)
                                            .show()
                                    }

                                    null -> {
                                        // Waiting for result
                                    }
                                }
                            }
                        }

                        // Confirmation dialog
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Confirm Delete") },
                                text = { Text("Are you sure you want to delete this tweet?") },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            viewModel.deleteTweet(tweetId, nodeimage.toString())
                                            showDialog = false // Close the dialog after confirming
                                        }
                                    ) {
                                        Text("Yes")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = { showDialog = false }) {
                                        Text("No")
                                    }
                                }
                            )
                        }
                    }
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp)
                ) {
                    Text(tweet.description, fontSize = 15.sp, fontWeight = FontWeight.W400)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (nodeimage == null) {

                    } else {
                        Image(
                            painter = rememberImagePainter(nodeimage),
                            contentDescription = "Tweet Image",
                            modifier = Modifier.height(300.dp),
                            alignment = Alignment.TopStart
                        )
                    }

                    Row {
                        Text(
                            DateUtil.getDateTime(tweet.timeStamp),
                            color = Color.Gray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.W400
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        CommentBox(tweetId = tweet.id)

                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            if (showMostLikedComment.value) {
                SideNode(tweetId = tweetId, navController = navController)
            }
        }
    }

    if (hasComments) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor =if (isFlipped) Color(0xFF4CAF50) else Color(0xFFEF4444)
            ),
            onClick = {
                isFlipped = !isFlipped
                showMostLikedComment.value = true
            }
        ) {
            Text(text = if (isFlipped) "Flip Node" else "Flip Node")
        }
    }

    Divider(modifier = Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth()
        .alpha(0.5f))

}


@Composable
fun SideNode(tweetId: String, navController : NavHostController ){
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))

    val comments by viewModel.getCommentsFlow(tweetId).collectAsState(initial = emptyList())
    LaunchedEffect(tweetId) {
        viewModel.getComment(tweetId)
        Log.d("CommentFetch", "Fetching comments for tweetId: $tweetId")
    }

    val data = comments.maxByOrNull { it.likeCount }
    val userimage = remember { mutableStateOf<Uri?>(Uri.parse("https://static.vecteezy.com/system/resources/thumbnails/005/545/335/small/user-sign-icon-person-symbol-human-avatar-isolated-on-white-backogrund-vector.jpg")) }
    val userUuid = data?.userId
    val userData by viewModel.getUserData2(uuid = userUuid.toString()).observeAsState()
    val username = userData?.name ?: ""

    Card(modifier = Modifier
        .graphicsLayer { scaleX = -1f },
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),
    ) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 2.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularImage(
                imageUri = userimage, imageSize = 38.dp,
                navController = navController,
                userUuid = userUuid.toString()
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(username,color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.W600, fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))

        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 50.dp)) {
            Text(data?.description.toString(),color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp, fontWeight = FontWeight.W400)
            Spacer(modifier = Modifier.height(4.dp))

        val time = data?.timeStamp?.toLong() ?: 0L
        Text(
            DateUtil.getDateTime(time),
            color = Color.Gray,
            fontSize = 11.sp,
            fontWeight = FontWeight.W400
        )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}


@Composable
fun CommentBox(
    tweetId: String
) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    var showDialog by remember { mutableStateOf(false) }
    val NodeText = remember { mutableStateOf("") }
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 96.dp, top = 4.dp)
            .clickable(onClick = { showDialog = true })
    ) {
        Text(
            text = "Add a Node...",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight(900)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
//            title = { Text("Add Node" )},
            text = {
                Column {
                    Text(text = "Add Node" , textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                       value = NodeText.value,
                       onValueChange = { NodeText.value = it },
                       label = { Text("Enter your Node...") }
                    )
                }
            },
            buttons = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 25.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(onClick = {
                        val comment = Comment(description = NodeText.value, userId = currentUserId)
                        viewModel.addComment(tweetId, comment).observeForever { isSuccess ->
                            if (isSuccess) {
                                // Comment added successfully
                            } else {
                                // Error adding comment
                            }
                        }
                        showDialog = false
                    }) {
                        Text("Add")
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }



            }
        )
    }
}