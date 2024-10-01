package com.example.wireup.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.NewsBox3
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(navController: NavHostController, tag: String) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val news1 by viewModel.news1.observeAsState(initial = emptyList())

    Column {
        TopAppBar(
            title = {
                Text(tag.toUpperCase(Locale.ROOT), fontSize = 18.sp)
            },
            navigationIcon = {
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
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            news1.filter { it.tags?.contains(tag) == true }
                .forEach { news1 ->
                    NewsBox3(news1, navController)
                }
        }

    }
}