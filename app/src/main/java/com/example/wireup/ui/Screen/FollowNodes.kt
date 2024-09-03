package com.example.wireup.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.TweetItem
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowNodes(navController: NavHostController) {

    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val tweets by viewModel.tweets.observeAsState(initial = emptyList())
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val users by viewModel.users.observeAsState(initial = emptyList())

    Column {
        TopAppBar(title = {
            Text(
                "Followers Nodes", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
            actions = {
                Row {
                    IconButton(onClick = { navController.navigate(NavigationItem.Node.route) }) {
                        Icon(painter = painterResource(id = R.drawable.global_network),
                            contentDescription = "globe",
                            modifier = Modifier.padding(8.dp))
                    }
                }
            }
        )
        Divider()

        tweets.forEach { tweet ->
            val user = users.find { it.id == tweet.userId }
            MainNode(tweet, navController = navController, user = user )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchTweetsForCurrentUser(currentUserId)
    }
}