package com.example.wireup.ui.Screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.CircularImage
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.example.wireup.util.DateUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.UUID

data class Tweet(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val userId: String,
    val comments: List<String> = emptyList(),
    val likeCount: Int = 0,
    val retweetCount: Int = 0,
    val bookmarkCount: Int = 0,
    val imageUrl: String? = null,
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
                                    .padding(end = 25.dp , bottom = 10.dp),
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
                    tweets.forEach { tweet ->
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
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val userimage = remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${user?.id}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userimage.value = uri
        }
    }
    val nodeimage = tweet.imageUrl
    val username = user?.name ?: ""
    val userUuid = user?.id
    var isFollowed by rememberSaveable {
        mutableStateOf(false)
    }
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
//        Button(
//            onClick = {
//                isFollowed = !isFollowed
//                viewModel.addFollowingToUser(userId = currentUserId, followingId = userUuid.toString())
//
//            },
//            colors = ButtonColors(
//                containerColor =
//                if (isFollowed) {
//                    MaterialTheme.colorScheme.background
//                } else {
//                    MaterialTheme.colorScheme.background
//                },
//                contentColor = if (isFollowed) {
//                    MaterialTheme.colorScheme.onBackground
//                } else {
//                    MaterialTheme.colorScheme.onBackground
//                },
//                disabledContentColor = Color.Gray,
//                disabledContainerColor = Color.LightGray
//            )
//        ) {
//            Text(if (isFollowed) "Following" else "Follow")
//        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 50.dp)) {
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

        Text(
            DateUtil.getDateTime(tweet.timeStamp),
            color = Color.Gray,
            fontSize = 11.sp,
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

}




