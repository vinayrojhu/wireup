package com.example.wireup.ui.Screen.profile

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
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
import com.example.wireup.ui.Screen.AudioScreen
import com.example.wireup.ui.Screen.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val userData by viewModel.getUserData().observeAsState()
    val userId = userData?.email?.split("@")?.get(0)
 //   val userId = FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    val username = userData?.name.toString()
    val userFollowers = "88230 Followers"
 //   val userImage = userData?.profileImage.toString()
 //   val userImage = FirebaseStorage.getInstance().reference.child("users/${FirebaseAuth.getInstance().currentUser?.uid}/profile_image").downloadUrl

    val userImage = remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${FirebaseAuth.getInstance().currentUser?.uid}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userImage.value = uri
        }
    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(userId.toString(), fontSize = 18.sp) },
                actions = {
                    var isRailExpanded by remember { mutableStateOf(false) }
                    var offsetX by remember { mutableStateOf(0.dp) }
                    var offsetY by remember { mutableStateOf(0.dp) }

                    Row {
                        IconButton(onClick = {
                            isRailExpanded = !isRailExpanded
                            offsetX = 16.dp // adjust x offset to match menu button position
                            offsetY = 48.dp // adjust y offset to match top app bar height
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }

                    if (isRailExpanded) {
                        Popup(
                            onDismissRequest = { isRailExpanded = false },
                            alignment = Alignment.TopEnd,
                            offset = IntOffset(16,48),
                        ) {
                            NavigationRail(
                                modifier = Modifier
                                    .height(300.dp)
                                    .width(140.dp),
                                header = {
                                    Text("Menu")
                                }
                            ) {
                                NavigationRailItem(
                                    icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "About") },
                                    label = { Text("About") },
                                    selected = false,
                                    onClick = {navController.navigate(NavigationItem.About.route)}
                                )

                                NavigationRailItem(
                                    icon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings") },
                                    label = { Text("Settings") },
                                    selected = false,
                                    onClick = {navController.navigate(NavigationItem.Settings.route)}
                                )
                                NavigationRailItem(
                                    icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Account") },
                                    label = { Text("Account") },
                                    selected = false,
                                    onClick = {navController.navigate(NavigationItem.Account.route)}
                                )
                                NavigationRailItem(
                                    icon = { Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Exit") },
                                    label = { Text("Logout") },
                                    selected = false,
                                    onClick = {FirebaseAuth.getInstance().signOut().run {
                                        navController.navigate(NavigationItem.Authentication.route)
                                    }}
                                )
                            }
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
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Column {
                            NavigationRailItem(
                                icon = { Icon(Icons.Filled.Person, contentDescription = "Friends") },
                                label = { Text("Friends") },
                                selected = false,
                                onClick = { navController.navigate(NavigationItem.Friends.route)}
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            NavigationRailItem(
                                icon = { Icon(Icons.Filled.Edit, contentDescription = "Edit") },
                                label = { Text("Edit Profile") },
                                selected = false,
                                onClick = { navController.navigate(NavigationItem.Edit.route) }
                            )

                        }
                        Spacer(modifier = Modifier.width(15.dp))

                            Image(
                                painter = rememberImagePainter(userImage.value), // Replace with actual resource ID
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .clip(CircleShape)
                            )


                        Spacer(modifier = Modifier.width(18.dp))
                        Column {
                            NavigationRailItem(
                                icon = { Icon(Icons.Filled.Share, contentDescription = "share") },
                                label = { Text("Share") },
                                selected = false,
                                onClick = { /* Handle account click */ }
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            NavigationRailItem(
                                icon = { Icon(painter = painterResource(id = R.drawable.save_wire),
                                    contentDescription = "Saved") },
                                label = { Text("Saved") },
                                selected = false,
                                onClick = {navController.navigate(NavigationItem.Saved.route)}
                            )
                        }

                    }

                    Text(
                        text = username,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = userFollowers,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

//            NavigationRail(
//                modifier = Modifier.weight(1f),
//                header = {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.Settings,
//                            contentDescription = "Settings"
//                        )
//                        Text(text = "Settings")
//                    }
//                },
//                content = {
//                    NavigationRailItem(
//                        icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
//                        label = { Text("Account") },
//                        selected = false,
//                        onClick = { /* Handle account click */ }
//                    )
//                    NavigationRailItem(
//                        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
//                        label = { Text("Settings") },
//                        selected = false,
//                        onClick = { /* Handle settings click */ }
//                    )
//                }
//            )


//            Row {
//                tabs.forEachIndexed { index, (text, icon) ->
//                    Tab(
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index },
//                        content = {
//                            Row(
//                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                if (selectedTabIndex != index) {
//                                    Icon(icon, contentDescription = null)
//                                } else {
//                                    Text(text)
//                                }
//                            }
//                        }
//                    )
//                }
//            }
//            when (selectedTabIndex) {
//                0 -> VideoScreen()
//                1 -> AudioScreen()
//                2 -> VideoScreen()
//            }

            TabView(
                imageWithText = listOf(
                    "VideoPodcast" to painterResource(id = R.drawable.node_icon6),
                    "AudioPodcast" to painterResource(id = R.drawable.podcastfinal),
                )
            ) {
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> UserNode()
                1 -> AudioScreen()
            }
        }
    }
}



@Composable
fun UserNode(){
    LazyColumn(
        modifier = Modifier
            .height(500.dp) // Set the desired height here
            .drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(70f, 0f),
                    end = Offset(70f, this.size.height)
                )
            }
    ) {
        val urls = listOf(
            "https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png",
            "https://vectorportal.com/storage/bD8Fzwwh5EDWD8YJkJfCCQCwQ8pxMUBIQaCbmKaZ.jpg",
            "https://vectorportal.com/storage/anime-avatar.jpg",
            "https://vectorportal.com/storage/aKJ32lYqZ7wSaC2f0NIMZEUh4hhjlVETzKZ3FjyR.jpg",
            "https://vectorportal.com/storage/UxFA72dcdu4df3y1hyEpAUYbpqSecbrhnjck3x77.jpg",
            "https://vectorportal.com/storage/Eg0cerMrth5t1FDREtUJQr8RmvmXXvA9XEsL7tcH.jpg",
            "https://cdn.pixabay.com/photo/2023/05/28/03/34/flowers-8022731_1280.jpg",
            "https://cdn.pixabay.com/photo/2023/07/14/10/50/flower-8126748_1280.jpg",
        )
        items(5) {
            TweetItem(tweet = Tweet(
                description = "Incorporate and convert Java code into #Kotlin using #Android Studio, and learn Kotlin language conventions along the way. You’ll also learn how to write Kotlin code to make it callable from Java code.",
                userId = FirebaseAuth.getInstance().currentUser?.email.toString(),
                comments = listOf("very nice"),
                likeCount = 203,
                retweetCount = 50,
                bookmarkCount = 135,
                imageUrl = "https://cdn.pixabay.com/photo/2023/07/14/10/50/flower-8126748_1280.jpg"), onCommentClick = { }) {}
        }
    }
}
