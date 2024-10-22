package com.example.wireup.ui.Screen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.CircularImage
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.example.wireup.util.DateUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(navController: NavHostController, tweetId: String) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val tweet by viewModel.tweet.observeAsState(null)
    val users by viewModel.users.observeAsState(initial = emptyList())
    val comments by viewModel.comment.collectAsState(initial = emptyList())

    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )



    LaunchedEffect(tweetId) {
        viewModel.getComments(tweetId)
    }
    viewModel.viewModelScope.launch {
        viewModel.fetchTweet(tweetId)
    }


    Column(Modifier.fillMaxHeight()) {
        TopAppBar(title = {
            Text(
                "Nodes", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        )

        Divider()

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
//                    .fillMaxHeight(0.30f)
                    .graphicsLayer {
                        this.rotationY = rotationY
                        cameraDistance = 12f * density
                    },
                contentAlignment = Alignment.Center
            ) {
                if (rotationY <= 90f) {
                    val user = users.find { it.id == tweet?.userId }
                    tweet?.let { MainComment(tweet = it, user = user, navController = navController) }
                }else{
                    SideNode(tweetId = tweetId, navController = navController)
                }
            }




            Button(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = if (isFlipped) Color(0xFF4CAF50) else Color(0xFFEF4444)
                ),
                onClick = { isFlipped = !isFlipped}
            ) {
                Text(text = if (isFlipped) "Flip Node" else "Flip Node")
            }
            Divider(Modifier.padding(top = 20.dp , bottom = 10.dp))

            if (comments != null && comments.isNotEmpty()) {
                comments.forEach { comment ->
                    val user = users.find { it.id == comment.userId }
                    CommentNode(data = comment, user = user, navController = navController, tweetId)
                }
            } else {
                // Show a loading indicator or an empty state message
                CircularProgressIndicator()
                // or
                Text("No comments found")
            }
        }


    }

}



@Composable
fun CommentNode(data: Comment, user: MUser?, navController : NavHostController, tweetId: String ){
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val likeResult by viewModel.likeCommentResult.observeAsState()
    val context = LocalContext.current
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val userimage = remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${user?.id}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userimage.value = uri
        }
    }
    LaunchedEffect(likeResult) {
        if (likeResult == true) {
            Toast.makeText(context, "Comment liked successfully!", Toast.LENGTH_SHORT).show()
        } else if (likeResult == false) {
            Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
        }
    }
    val username = user?.name ?: ""
    val userUuid = user?.id
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
            Text(username, fontWeight = FontWeight.W600, fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))

//        Button(onClick = {
//            viewModel.likeComment(tweetId, data.id, currentUserId)
//        }) {
////            Text(text = "Like")
//            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription ="like" )
//        }

        var isSubnodeLiked by remember { mutableStateOf(false) }
        Icon(imageVector = if (isSubnodeLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            tint = if (isSubnodeLiked) Color.Red else Color.Gray,
            contentDescription ="like" , modifier = Modifier
                .clickable(onClick = {
                    viewModel.likeComment(tweetId, data.id, currentUserId)
                    isSubnodeLiked = true

                })
                .alpha(0.8f)
                .size(22.dp) )

        Spacer(modifier = Modifier.width(2.dp))
        Text(text = data.likeCount.toString(), fontSize = 13.sp)
        Spacer(modifier = Modifier.width(10.dp))
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 50.dp)) {
        Text(data.description, fontSize = 15.sp, fontWeight = FontWeight.W400)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = DateUtil.getDateTime(data.timeStamp),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.alpha(0.5f),
            fontSize = 11.sp,
            fontWeight = FontWeight.W400
        )

        Divider(modifier = Modifier
            .padding(8.dp)
            .alpha(0.5f))
    }


}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainComment(tweet: Tweet, user: MUser?, navController : NavHostController ){
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val context = LocalContext.current
    val userimage = remember { mutableStateOf<Uri?>(null) }
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${user?.id}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userimage.value = uri
        }
    }
    val nodeimage = tweet.imageUrl
    val username = user?.name ?: ""
    val userUuid = user?.id
    val tweetId = tweet.id

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
    val mostLikedComment = getMostLikedComment(tweet.id) ;


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
                    if (currentUserId == tweet.userId) {
                        Column(modifier = Modifier
                            .clickable { viewModel.deleteTweet(tweetId, nodeimage.toString()) }) {

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
                                    )
                                        .show()
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

}