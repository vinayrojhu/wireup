package com.example.wireup.ui.Screen

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wireup.model.Follower
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FriendsScreen(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    LaunchedEffect(Unit) {
        viewModel.getFollowersOfUser(currentUserId)
    }

    val followers by viewModel.followerss.collectAsState()

    Column {
        TopAppBar(title = {
            Text(
                "Followers", fontSize = 18.sp
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
        // ...
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (followers.isEmpty()) {
                Text("No Followers Yet", fontSize = 18.sp)
            } else {

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(followers) { follower ->
                        val user by viewModel.getUser(follower).collectAsState(initial = null)

                        user?.let { u ->
                            var imageUri by remember { mutableStateOf<Uri?>(null) }

                            LaunchedEffect(Unit) {
                                if (u.profileImage.startsWith("https://firebasestorage.googleapis.com/")) {
                                    val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(u.profileImage)
                                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                                        imageUri = uri
                                    }.addOnFailureListener { e ->
                                        Log.e("FriendsScreen", "Failed to download image", e)
                                    }
                                } else {
                                    imageUri = Uri.parse(u.profileImage)
                                }
                            }

                            FollowerItem(
                                follower = Follower(
                                    followerId = u.uniqueId,
                                    image = imageUri,
                                    name = u.name
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FollowerItem(follower: Follower) {
    Log.d("FollowerItem", "Displaying follower: ${follower.followerId}")
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image
        if (follower.image != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(follower.image)
                    .build(),
                contentDescription = "Follower image",
                modifier = Modifier.size(40.dp)
            )
        } else {
            // Display a placeholder or a loading indicator
            CircularProgressIndicator(modifier = Modifier.size(40.dp))
        }

        // Name
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = follower.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            // You can add more details like follower's email or other relevant information here
        }
    }
}
