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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.collectAsState
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
import com.example.wireup.ui.Screen.MainNode
import com.example.wireup.ui.Screen.PodcastItem
import com.example.wireup.ui.Screen.Tweet
import com.example.wireup.ui.Screen.audiopodcasts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.multibindings.LazyClassKey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    val tweets by viewModel.tweets.observeAsState(initial = emptyList())
    val users by viewModel.users.observeAsState(initial = emptyList())


    var selectedTabIndex by remember { mutableStateOf(0) }

    val userData by viewModel.getUserData().observeAsState()
    val userId = userData?.uniqueId.toString()
    val username = userData?.name.toString()

    val userImage = remember { mutableStateOf<Uri?>(null) }

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()


    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${FirebaseAuth.getInstance().currentUser?.uid}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userImage.value = uri
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchTweetsofCurrentUser(userData?.id.toString())
    }

    LaunchedEffect(Unit) {
        viewModel.getFollowerCount(userData?.id.toString())
    }

    val followerCount = viewModel.followerCount.collectAsState().value

    val userFollowers = "Followers: $followerCount"

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
                                painter = rememberImagePainter(if (userImage.value == null) "https://static.vecteezy.com/system/resources/thumbnails/005/545/335/small/user-sign-icon-person-symbol-human-avatar-isolated-on-white-backogrund-vector.jpg" else userImage.value),
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

//                            NavigationRailItem(
//                                icon = { Icon(painter = painterResource(id = R.drawable.save_wire),
//                                    contentDescription = "Saved" )
//                                       },
//                                label = { Text("Saved") },
//                                selected = false,
//                                onClick = {navController.navigate(NavigationItem.Saved.route)}
//                            )


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
                0 -> Column {

                        val filteredTweets = tweets.filter { it.userId == currentUserId }

                        filteredTweets.forEach { tweet ->
                            val user = users.find { it.id == tweet.userId }
                            MainNode(tweet, user, navController)
                        }

                }
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
