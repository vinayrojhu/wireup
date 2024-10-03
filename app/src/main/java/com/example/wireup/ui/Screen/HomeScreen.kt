package com.example.wireup.ui.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
                        .fillMaxWidth(0.6f)
                        .padding(0.dp)
                        .background(color = MaterialTheme.colorScheme.background)
                        .clickable { }
                ) {
                    Text(text = "WIREup", style = MaterialTheme.typography.displaySmall ,
                        modifier = Modifier.padding(start = 10.dp , top = 10.dp))

                    Spacer(modifier = Modifier.height(15.dp))

                    Column() {
                        // Add more sidebar content here

                        Spacer(modifier = Modifier.height(15.dp))

                        Column(modifier = Modifier
                            .clickable { navController.navigate(NavigationItem.Tags.route + "/trending") }
                            .fillMaxWidth()) {

                            Text(text = "Trending", style = MaterialTheme.typography.titleMedium  , modifier = Modifier.padding(start = 20.dp))
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier
                            .clickable { navController.navigate(NavigationItem.Tags.route + "/world") }
                            .fillMaxWidth()) {
                            Text(text = "World", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier
                            .clickable { navController.navigate(NavigationItem.Tags.route + "/politics") }
                            .fillMaxWidth()) {
                            Text(text = "Politics", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        Column(modifier = Modifier
                            .clickable { navController.navigate(NavigationItem.FlipFlop.route) }
                            .fillMaxWidth()) {
                            Text(text = "FLIP-FLOP", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight(800) , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }



                        Column(modifier = Modifier
                            .clickable { navController.navigate(NavigationItem.Tags.route + "/business") }
                            .fillMaxWidth()) {
                            Text(text = "Business", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier
                            .clickable { navController.navigate(NavigationItem.Tags.route + "/health&lifestyle") }
                            .fillMaxWidth()) {
                            Text(text = "Health & Lifestyle", style = MaterialTheme.typography.titleMedium , modifier = Modifier.padding(start = 20.dp) )
                            Spacer(modifier = Modifier.height(15.dp))
                        }

                        Column(modifier = Modifier
                            .fillMaxWidth()) {

                            var expanded by remember { mutableStateOf(false) }

                            Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth(0.9f)) {
                                Text(text = "Sports",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .clickable { navController.navigate(NavigationItem.Tags.route + "/sports")})
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "arrow",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .clickable(onClick = { expanded = !expanded })
                                )

                            }

                            if (expanded) {
                                FlowRow(modifier = Modifier.fillMaxWidth(0.9f)
                                    .padding(start = 15.dp , top = 5.dp )
                                ){

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp , bottom = 7.dp)
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/featured") }
                                    ) {
                                        Text(
                                            text = "Featured Sports",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp , bottom = 7.dp)
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/cricket") }
                                    ) {
                                        Text(
                                            text = "Cricket",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                        )

                                    }


//                               end row options as these shouldnot have bottom padding

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp )
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/Football") }
                                    ) {
                                        Text(
                                            text = "Football",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp )
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/Awards") }
                                    ) {
                                        Text(
                                            text = "Awards",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }


                                }
                            }

                            Spacer(modifier = Modifier.height(15.dp))
                        }




                        Column(modifier = Modifier
                            .fillMaxWidth()) {

                            var expanded by remember { mutableStateOf(false) }

                            Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth(0.9f)) {
                                Text(text = "IN INDIA",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .clickable { navController.navigate(NavigationItem.Tags.route + "/india") })
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "arrow",
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .clickable(onClick = { expanded = !expanded })
                                    )

                            }

                            if (expanded) {
                                FlowRow(modifier = Modifier.fillMaxWidth(0.9f)
                                        .padding(start = 15.dp , top = 5.dp )
                                ){

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp , bottom = 7.dp)
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/Delhi") }
                                    ) {
                                        Text(
                                        text = "New Delhi",
                                        fontSize = 13.sp,
                                        modifier = Modifier.padding(5.dp)
                                            )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp , bottom = 7.dp)
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/Hyderabad") }
                                    ) {
                                        Text(
                                            text = "Hyderabad",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                               )

                                    }


//                               end row options as these shouldnot have bottom padding

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp )
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/Banglore") }
                                    ) {
                                        Text(
                                            text = "Banglore",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .padding(end =7.dp )
                                            .border(BorderStroke(2.dp, Color.LightGray) , shape = RoundedCornerShape(15.dp))
                                            .padding(horizontal = 3.dp , vertical = 0.3.dp)
                                            .clickable { navController.navigate(NavigationItem.Tags.route + "/Hyderabad") }
                                    ) {
                                        Text(
                                            text = "Mumbai",
                                            fontSize = 13.sp,
                                            modifier = Modifier.padding(5.dp)
                                               )
                                    }


                                }
                            }

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
                                        color = MaterialTheme.colorScheme.onBackground,
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
}



