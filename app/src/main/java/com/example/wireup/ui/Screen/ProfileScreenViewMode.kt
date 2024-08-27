package com.example.wireup.ui.Screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenViewMode(navController: NavHostController, userId: String) {

    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))

    val userData by viewModel.getUserData2(uuid = userId).observeAsState()
    val userImage = remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/$userId/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userImage.value = uri
        }
    }

    val followers by viewModel.followers.observeAsState(initial = emptyList())

    val userFollowers = "Followers: ${followers.size ?: 0}"
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(userData?.name.toString(), fontSize = 18.sp) },
                actions = {
                    // You can add a back button here if you want
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(modifier = Modifier.height(20.dp))
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
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = userData?.name.toString(),
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = userFollowers,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row {
                    if (currentUserId != userData?.id){
                        NavigationRailItem(
                            icon = { Icon(Icons.Outlined.Add, contentDescription = "Follow") },
                            label = { Text("Follow") },
                            selected = false,
                            onClick = { viewModel.followUser(currentUserId) }
                        )
                    }

//                    NavigationRailItem(
//                        icon = { Icon(Icons.Outlined.Person, contentDescription = "Following") },
//                        label = { Text("Following") },
//                        selected = false,
//                        onClick = { navController.navigate(NavigationItem.Friends.route)}
//                    )


                    NavigationRailItem(
                        icon = { Icon(Icons.Outlined.Share, contentDescription = "share") },
                        label = { Text("Share") },
                        selected = false,
                        onClick = { /* Handle account click */ }
                    )



                }
                Spacer(modifier = Modifier.height(20.dp))
                Divider()
            }


            // You can add other user details here
        }
    }
}