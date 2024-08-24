package com.example.wireup.ui.Screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.profile.UserViewModel
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

            Text(
                text = userData?.name.toString(),
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = userFollowers,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            // You can add other user details here
        }
    }
}