package com.example.wireup.Navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.AboutScreen
import com.example.wireup.ui.Screen.AccountScreen
import com.example.wireup.ui.Screen.EditProfileScreen
import com.example.wireup.ui.Screen.FlipFlopScreen
import com.example.wireup.ui.Screen.FollowNodes
import com.example.wireup.ui.Screen.FriendsScreen
import com.example.wireup.ui.Screen.HelpScreen
import com.example.wireup.ui.Screen.HomeScreen
import com.example.wireup.ui.Screen.LikedScreen
import com.example.wireup.ui.Screen.NodeScreen
import com.example.wireup.ui.Screen.OpenAudioPodcast
import com.example.wireup.ui.Screen.OpenVideoPodcast
import com.example.wireup.ui.Screen.PodcastScreen
import com.example.wireup.ui.Screen.ProfileScreenViewMode
import com.example.wireup.ui.Screen.ReadMore
import com.example.wireup.ui.Screen.SavedScreen
import com.example.wireup.ui.Screen.SearchScreen
import com.example.wireup.ui.Screen.SettingsScreen
import com.example.wireup.ui.Screen.SplashScreen
import com.example.wireup.ui.Screen.TagsScreen
import com.example.wireup.ui.Screen.login.AuthenticationScreen
import com.example.wireup.ui.Screen.profile.ProfileScreen
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var isSplashScreenFinished by rememberSaveable {
        mutableStateOf(false)
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isSplashScreenFinished) {
            if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
                NavigationItem.Authentication.route
            }else {
                NavigationItem.Home.route
            }
        } else {
            NavigationItem.Splash.route
        }
    ) {
        composable(NavigationItem.Splash.route){
            SplashScreen {
                isSplashScreenFinished = true
            }
        }
        composable(NavigationItem.Authentication.route , enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            AuthenticationScreen(navController = navController)
        }


        composable(NavigationItem.Home.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            HomeScreen( navController = navController, viewModel = UserViewModel(
                FirestoreRepository()
            ))
        }


        composable(NavigationItem.FlipFlop.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            FlipFlopScreen( navController = navController)
        }


        composable(NavigationItem.Podcast.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            PodcastScreen( navController = navController)
        }

        composable(NavigationItem.Profile.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            ProfileScreen( navController = navController, viewModel = UserViewModel(
                FirestoreRepository()
            ))
        }

        composable(NavigationItem.Search.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            SearchScreen( navController = navController)
        }

        composable(NavigationItem.Edit.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            EditProfileScreen( navController = navController)
        }

        composable(NavigationItem.Tags.route + "/{newsId}", enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {backStackEntry ->
            val tag = backStackEntry.arguments?.getString("newsId").toString()
            TagsScreen( navController = navController, tag)
        }




        composable(NavigationItem.Node.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            NodeScreen(navController = navController, viewModel = UserViewModel(
                FirestoreRepository()
            ))
        }
        composable(NavigationItem.Friends.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            FriendsScreen( navController = navController)
        }

        composable(NavigationItem.Saved.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            SavedScreen( navController = navController)
        }
        composable(NavigationItem.Settings.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            SettingsScreen( navController = navController)
        }
        composable(NavigationItem.About.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            AboutScreen( navController = navController)
        }
        composable(NavigationItem.Account.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            AccountScreen( navController = navController)
        }

        composable(NavigationItem.ReadMore.route + "/{newsId}", enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId").toString()
            ReadMore(navController = navController, newsId)
        }
        composable(NavigationItem.VideoPodcastOpened.route + "/{videoLink}/{heading}",
            arguments = listOf(
                navArgument("videoLink") { type = NavType.StringType },
                navArgument("heading") { type = NavType.StringType }
            ), enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }

        ) { backStackEntry ->
            val videoLink = backStackEntry.arguments?.getString("videoLink").toString()
            val videoHeading = backStackEntry.arguments?.getString("heading").toString()
            OpenVideoPodcast(navController = navController, videoLink, videoHeading)
        }
        composable(NavigationItem.AudioPodcastOpened.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){
            OpenAudioPodcast(navController = navController)
        }

        composable(NavigationItem.ProfileViewMode.route + "/{userId}", enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) { entry ->
            ProfileScreenViewMode(navController, entry.arguments?.getString("userId")!!)
        }

        composable(NavigationItem.Liked.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            LikedScreen( navController = navController)
        }

        composable(NavigationItem.FollowNodes.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){
            FollowNodes(navController = navController)
        }

        composable(NavigationItem.Help.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){
            HelpScreen(navController = navController)
        }


    }
}