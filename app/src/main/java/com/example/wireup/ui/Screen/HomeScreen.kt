package com.example.wireup.ui.Screen

import android.widget.Toast
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.NewsData
import com.example.wireup.ui.Components.NewsBox
import com.example.wireup.ui.Components.NewsBox2
import com.example.wireup.ui.Components.NewsBox3
import com.example.wireup.ui.Components.ScrollingNewsBox
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    val tabIndex = remember { mutableStateOf(0) }
    val userData by viewModel.getUserData().observeAsState()
    val username = userData?.name.toString()
    val NewsBoxData = NewsData(
        category ="Philosophy" ,
        trending = true ,
        imageurl = "https://i.abcnewsfe.com/a/3cb8ba6c-ccdb-48de-99cc-e684e5358708/abcnl__NEW_streamingnow_1664457649883_hpMain_16x9.jpg?w=608" ,
        date = "2/10/2024",
        title = "8 Steps To Help You Stop Overthinking Everything",
        subtitle = "Learn how to manage your thoughts and reduce anxiety."
    )
    Column(modifier = Modifier) {
        TopAppBar(title = {
            Text(
                "WIREup",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
            )
        },
//            navigationIcon = {
//                Icon(imageVector = Icons.Default.Menu, contentDescription = "Search")

//            },
            actions = {

            Row {
                IconButton(onClick = {
                    navController.navigate(NavigationItem.Search.route)

                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }

                IconButton(onClick = {
                    navController.navigate(NavigationItem.Search.route)

                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }


            }
        })
        Divider()


        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                            ScrollingNewsBox(NewsBoxData, navController =navController )
                        }
                        NewsBox2(NewsBoxData , navController =navController)
                        NewsBox2(NewsBoxData, navController =navController)
                        NewsBox2(NewsBoxData, navController =navController)
                        NewsBox2(NewsBoxData , navController =navController)
                        NewsBox2(NewsBoxData, navController =navController)

                        NewsBox3(NewsBoxData , navController =navController)
                        NewsBox3(NewsBoxData , navController =navController)
                        NewsBox3(NewsBoxData , navController =navController)

                        NewsBox3(NewsBoxData , navController =navController)
                        NewsBox3(NewsBoxData , navController =navController)
                        NewsBox3(NewsBoxData , navController =navController)

//                        NewsBox(NewsBoxData, navController = navController)
//                        NewsBox(NewsBoxData, navController = navController)
//                        NewsBox(NewsBoxData, navController = navController)
//                        NewsBox(NewsBoxData, navController = navController)
                    }
                    1 -> Column {
                        NewsBox(NewsBoxData, navController = navController)
                    }
                    2 -> Column {
                        NewsBox(NewsBoxData, navController = navController)
                        NewsBox(NewsBoxData, navController = navController)
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

