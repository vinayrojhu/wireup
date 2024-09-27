package com.example.wireup.ui.Screen.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Components.TabView
import com.example.wireup.ui.Components.TweetItem
import com.example.wireup.ui.Screen.PodcastItem
import com.example.wireup.ui.Screen.Tweet
import com.example.wireup.ui.Screen.audiopodcasts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val userData by viewModel.getUserData().observeAsState()
    val userId = userData?.uniqueId.toString()
    val username = userData?.name.toString()

    val userImage = remember { mutableStateOf<Uri?>(null) }



    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${FirebaseAuth.getInstance().currentUser?.uid}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userImage.value = uri
        }
    }

    val userFollowers = "Followers: 7"

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val userIdToFollow = userData?.id.toString()





    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(userId, fontSize = 18.sp) },
                actions = {

                    Row {
                        IconButton(onClick = {
                            navController.navigate(NavigationItem.Settings.route)
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }

                }
            )
        }
    ){ innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider()
                Column(verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Column {

                        Spacer(modifier = Modifier.width(15.dp))

                            Image(
                                painter = rememberImagePainter(userImage.value), // Replace with actual resource ID
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .clip(CircleShape)
                                    .align(alignment = Alignment.CenterHorizontally),
                                contentScale = ContentScale.Crop
                            )

                            Column(modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .padding(top = 10.dp, bottom = 10.dp)) {
                                Row(modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)) {
                                    Text(
                                        text = username,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }

                                Text(
                                    text = userFollowers,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )

                            }



                        Row {

                            NavigationRailItem(
                                icon = { Icon(Icons.Outlined.Person, contentDescription = "Following") },
                                label = { Text("Followers") },
                                selected = false,
                                onClick = { navController.navigate(NavigationItem.Friends.route)}
                            )

                            NavigationRailItem(
                                icon = { Icon(painter = painterResource(id = R.drawable.save_wire),
                                    contentDescription = "Saved" )
                                       },
                                label = { Text("Saved") },
                                selected = false,
                                onClick = {navController.navigate(NavigationItem.Saved.route)}
                            )


                            NavigationRailItem(
                                icon = { Icon(Icons.Outlined.Share, contentDescription = "share") },
                                label = { Text("Share") },
                                selected = false,
                                onClick = {  }
                            )
                        }

                    }


                }


            TabView(
                imageWithText = listOf(
                    "VideoPodcast" to painterResource(id = R.drawable.node_icon6),
                    "AudioPodcast" to painterResource(id = R.drawable.podcastfinal),
                )
            ) {
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> UserNode(navController)
                1 -> LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    items(audiopodcasts) { Apodcast ->
                        PodcastItem(Apodcast,navController)
                    }
                }
            }
        }
    }
}



@Composable
fun UserNode(navController: NavHostController){
    LazyColumn(
        modifier = Modifier
            .height(500.dp) // Set the desired height here
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(150f, 0f),
                    end = Offset(150f, this.size.height)
                )
            }
    ) {

        items(5) {
            TweetItem(tweet = Tweet(
                description = "Incorporate and convert Java code into #Kotlin using #Android Studio, and learn Kotlin language conventions along the way. You’ll also learn how to write Kotlin code to make it callable from Java code.",
                userId = FirebaseAuth.getInstance().currentUser?.email.toString(),
                comments = listOf("very nice"),
                likeCount = 203,
                retweetCount = 50,
                bookmarkCount = 135,
                imageUrl = "https://cdn.pixabay.com/photo/2023/07/14/10/50/flower-8126748_1280.jpg"), onCommentClick = { }, navController = navController) {}
        }
    }
}

fun shareAccount(context: android.content.Context, username: String, userid: String) {
    val shareText = "Check out my account!\nUsername: $username\nuserid: $userid"

    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    // Use createChooser to display all available apps
    val chooserIntent = Intent.createChooser(intent, "Share via")
    context.startActivity(chooserIntent)
}
