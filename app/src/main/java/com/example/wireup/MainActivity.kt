package com.example.wireup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wireup.Navigation.AppNavHost
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.Navigation.Screen
import com.example.wireup.ui.Components.BottomNavItem
import com.example.wireup.ui.Components.BottomNavigationBar
import com.example.wireup.ui.theme.WireUpTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            WireUpTheme {
                SetupTransparentSystemUi(
                    systemUiController = rememberSystemUiController(),
                    actualBackgroundColor = MaterialTheme.colorScheme.surface
                )
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val screens = listOf(
                        NavigationItem.Home.route,
                        NavigationItem.Podcast.route,
                        NavigationItem.FlipFlop.route,
                        NavigationItem.Profile.route,
                        NavigationItem.Node.route,
                        NavigationItem.Friends.route,
                        NavigationItem.Saved.route,
                        NavigationItem.Search.route

                    )
                    val showBottomBar = navController
                        .currentBackStackEntryAsState().value?.destination?.route in screens.map { it }
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = showBottomBar,
                                enter = fadeIn() + scaleIn(),
                                exit = fadeOut() + scaleOut(),
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .fillMaxWidth()
                                ) {
                                    BottomNavigationBar(
                                        items = listOf(
                                            BottomNavItem(
                                                NavigationItem.Home.route,
                                                Screen.HOME.name,
                                                icon = painterResource(id = R.drawable.icons8_newspaper_48)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Podcast.route,
                                                Screen.PODCAST.name,
                                                icon = painterResource(id = R.drawable.podcastfinal)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Node.route,
                                                Screen.NODE.name,
                                                icon = painterResource(id = R.drawable.node_iconn)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Profile.route,
                                                Screen.PROFILE.name,
                                                icon = painterResource(id = R.drawable.usericon)
                                            ),
                                        ),
                                        navController = navController,
                                        onItemClick = {
                                            if (it.route == NavigationItem.Profile.route) {
                                                navController.navigate(
//                                                    "${NavigationItem.Profile.route}/$MY_USER_ID"
                                                    NavigationItem.Profile.route
                                                )
                                            } else {
                                                navController.navigate(it.route)
                                            }
                                        }

                                    )
                                }
                            }
                        }
                    ) {
                        AppNavHost(
                          //  homeViewModel = homeViewModel,
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}


@Composable
internal fun SetupTransparentSystemUi(
    systemUiController: SystemUiController = rememberSystemUiController(),
    actualBackgroundColor: Color,
) {
    val minLuminanceForDarkIcons = .5f

    SideEffect {
        systemUiController.setStatusBarColor(
            color = actualBackgroundColor,
            darkIcons = actualBackgroundColor.luminance() > minLuminanceForDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = actualBackgroundColor,
            darkIcons = actualBackgroundColor.luminance() > minLuminanceForDarkIcons,
            navigationBarContrastEnforced = false
        )
    }
}