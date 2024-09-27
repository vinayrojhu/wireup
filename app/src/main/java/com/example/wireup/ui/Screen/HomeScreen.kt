package com.example.wireup.ui.Screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.ui.Components.NewsBox2
import com.example.wireup.ui.Components.NewsBox3
import com.example.wireup.ui.Components.ScrollingNewsBox
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    val news1 by viewModel.news1.observeAsState(initial = emptyList())
    val tabIndex = remember { mutableStateOf(0) }
    val userData by viewModel.getUserData().observeAsState()
    val username = userData?.name.toString()
    val scrollState = rememberScrollState()



    Column(modifier = Modifier) {
        TopAppBar(title = {
            Text(
                "WIREup",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
            )
        }, actions = {
            Row {
                IconButton(onClick = {
                    navController.navigate(NavigationItem.Search.route)

                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        })
        Divider()


        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Column(modifier = Modifier.padding(20.dp)) {
//                Text(text = "Saturday, March 24th" , fontWeight = FontWeight.W300 , fontSize = 12.sp)
                Text(
                    DateUtil.getDate(System.currentTimeMillis()),
                    color = Color.Black,
                    fontSize = 12.sp ,
                    fontWeight = FontWeight.W300
                )
                Text(text = "Welcome Back , " , fontSize = 28.sp , fontWeight =FontWeight.W600)
                Text(text = username, fontSize = 28.sp , fontWeight =FontWeight.W600)
            }

            Box(modifier = Modifier.fillMaxHeight()){

                when (tabIndex.value) {
                    0 -> Column() {
                        Row(modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(bottom = 20.dp)) {

                            news1.filter { it.tags?.contains("scroll") == true }
                                .take(5)
                                .forEach { news1 ->
                                ScrollingNewsBox(news1, navController)
                            }
                        }

                        news1.filter { it.tags?.contains("trending") == true }
                            .take(5)
                            .forEach { news1 ->
                            NewsBox2(news1, navController)
                        }

                        news1.filter { it.tags?.contains("all") == true }
                            .forEach { news1 ->
                                NewsBox3(news1, navController)
                            }


                    }
                }
            }
        }

    }













//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.White)
//        ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.White)
//        ) {
//            // Your screen content here
//
//            BottomNavigation(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = Color.Transparent),
//                elevation = 0.dp
//            ) {
//                TabRow(
//                    selectedTabIndex = tabIndex.value
//                ) {
//                    Tab(
//                        selected = tabIndex.value == 0,
//                        onClick = { tabIndex.value = 0 },
////                        icon = {
////                            Icon(
////                                imageVector = Icons.Default.Home,
////                                contentDescription = "Home"
////                            )
////                        },
//                        text = { Text(text = "Home") }
//                    )
//                    Tab(
//                        selected = tabIndex.value == 1,
//                        onClick = { tabIndex.value = 1 },
////                        icon = {
////                            Icon(
////                                imageVector = Icons.Default.ShoppingCart,
////                                contentDescription = "Shopping Cart"
////                            )
////                        },
//                        text = { Text(text = "Flip-Flop") }
//                    )
//                    Tab(
//                        selected = tabIndex.value == 2,
//                        onClick = { tabIndex.value = 2 },
////                        icon = {
////                            Icon(
////                                imageVector = Icons.Default.AccountBox,
////                                contentDescription = "Account"
////                            )
////                        },
//                        text = { Text(text = "Research") }
//                    )
//                }
//            }
//            when (tabIndex.value) {
//                0 -> Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                    NewsBox(NewsBoxData)
//                    NewsBox(NewsBoxData)
//                    NewsBox(NewsBoxData)
//                    NewsBox(NewsBoxData)
//                }
//                1 -> Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                    NewsBox(NewsBoxData)
//                }
//                2 -> Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                    NewsBox(NewsBoxData)
//                    NewsBox(NewsBoxData)
//                }
//            }
//        }
//      }

}

