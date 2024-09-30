package com.example.wireup.ui.Screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Components.NewsBox2
import com.example.wireup.ui.Components.NewsBox3
import com.example.wireup.ui.Components.ScrollingNewsBox
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.util.DateUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( navController: NavHostController, viewModel: UserViewModel = viewModel()) {

    val news1 by viewModel.news1.observeAsState(initial = emptyList())
    val tabIndex = remember { mutableStateOf(0) }
    val userData by viewModel.getUserData().observeAsState()
    val username = userData?.name.toString()
    val scrollState = rememberScrollState()



    Column(modifier = Modifier) {

        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        // ModalNavigationDrawer for Material3
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                // Sidebar content
                var expanded by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                        .padding(0.dp)
                        .background(Color.White)
                        .clickable {  }

                ) {
                    Text(text = "WIREup", style = MaterialTheme.typography.displaySmall ,
                        modifier = Modifier.padding(start = 10.dp , top = 10.dp))

                    Spacer(modifier = Modifier.height(15.dp))

                    Column() {
                        // Add more sidebar content here

                        Spacer(modifier = Modifier.height(15.dp))

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "Home", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "Trending", style = MaterialTheme.typography.titleMedium  , modifier = Modifier.padding(start = 20.dp))
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "World", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "Politics", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "FLIP-FLOP", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight(800) , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "Sports", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "Business", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "IN INDIA", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier.clickable { }.fillMaxWidth()) {
                            Text(text = "Health & Lifestyle", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }

                }
            },
            content = {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("WIREup", fontWeight = FontWeight(800)) },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                            navigationIcon = {
                                IconButton(onClick = {
                                    // Open the sidebar when button is clicked
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu Icon"
                                    )
                                }

                            },
                            actions = {
                                // Search IconButton
                                IconButton(onClick = {
                                    navController.navigate(NavigationItem.Search.route)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search Icon"
                                    )
                                }
                            }
                        )
                    },
                    content = { padding ->
                        // Main content
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
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
                    }
                )
            }
        )
    }
//        TopAppBar(title = {
//            Text(
//                "WIREup",
//                fontFamily = FontFamily.Monospace,
//                fontWeight = FontWeight.ExtraBold,
//            )
//        },
////            navigationIcon = {
////                Icon(imageVector = Icons.Default.Menu, contentDescription = "Search")
//
////            },
//            actions = {
//
//            Row {
//                IconButton(onClick = {
//                    navController.navigate(NavigationItem.Search.route)
//
//                }) {
//                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
//                }
//
//                IconButton(onClick = {
//                }) {
//                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
//                }
//
//
//            }
//        })
//        Divider()
//
//        Column(modifier = Modifier.verticalScroll(scrollState)) {
//            Column(modifier = Modifier.padding(20.dp)) {
////                Text(text = "Saturday, March 24th" , fontWeight = FontWeight.W300 , fontSize = 12.sp)
//                Text(
//                    DateUtil.getDate(System.currentTimeMillis()),
//                    color = Color.Black,
//                    fontSize = 12.sp ,
//                    fontWeight = FontWeight.W300
//                )
//                Text(text = "Welcome Back , " , fontSize = 28.sp , fontWeight =FontWeight.W600)
//                Text(text = username, fontSize = 28.sp , fontWeight =FontWeight.W600)
//            }
//
//            Box(modifier = Modifier.fillMaxHeight()){
//
//                when (tabIndex.value) {
//                    0 -> Column() {
//                        Row(modifier = Modifier
//                            .horizontalScroll(rememberScrollState())
//                            .padding(bottom = 20.dp)) {
//
//                            news1.filter { it.tags?.contains("scroll") == true }
//                                .take(5)
//                                .forEach { news1 ->
//                                ScrollingNewsBox(news1, navController)
//                            }
//                        }
//
//                        news1.filter { it.tags?.contains("trending") == true }
//                            .take(5)
//                            .forEach { news1 ->
//                            NewsBox2(news1, navController)
//                        }
//
//                        news1.filter { it.tags?.contains("all") == true }
//                            .forEach { news1 ->
//                                NewsBox3(news1, navController)
//                            }
//
//
//                    }
//                }
//            }
//        }

    }




//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBarWithSidebarMaterial3Example() {
//    // Drawer state for Material3
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//
//    // ModalNavigationDrawer for Material3
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            // Sidebar content
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(16.dp)
//                    .background(Color.LightGray)
//            ) {
//                Text(text = "Sidebar Item 1", style = MaterialTheme.typography.titleMedium)
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(text = "Sidebar Item 2", style = MaterialTheme.typography.titleMedium)
//                Spacer(modifier = Modifier.height(10.dp))
//                // Add more sidebar content here
//            }
//        },
//        content = {
//            Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = { Text("WIREup", fontWeight = FontWeight(800)) },
//                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
//                        navigationIcon = {
//                            IconButton(onClick = {
//                                // Open the sidebar when button is clicked
//                                scope.launch { drawerState.open() }
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.Menu,
//                                    contentDescription = "Menu Icon"
//                                )
//                            }
//                            IconButton(onClick = {
//                                navController.navigate(NavigationItem.Search.route)
//
//                            }) {
//                                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
//                            }
//                        }
//                    )
//                },
//                content = { padding ->
//                    // Main content
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(padding)
//                    ) {
//                        Text("Main content goes here")
//                    }
//                }
//            )
//        }
//    )
//}





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



