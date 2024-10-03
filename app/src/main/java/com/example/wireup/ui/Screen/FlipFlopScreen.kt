package com.example.wireup.ui.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.FlipCard
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
fun FlipFlopScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val flipNews by viewModel.flipNews.observeAsState(initial = emptyList())


    LaunchedEffect(Unit) {
        Toast.makeText(context, "Swipe for different news", Toast.LENGTH_SHORT).show()
    }

    Column {
        TopAppBar(
            title = {
                Text("Flip-Flop", fontSize = 18.sp)
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

        Divider()

        HorizontalPager(
            count = flipNews.size,
            state = PagerState(0),
            modifier = Modifier.fillMaxSize()
        ) { page ->
            FlipCard(flipNews[page])
        }

    }
}