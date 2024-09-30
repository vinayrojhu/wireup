package com.example.wireup.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.example.wireup.ui.Screen.ReadMore
import com.example.wireup.ui.Screen.profile.ProfileScreen
import com.example.wireup.ui.Screen.SavedScreen
import com.example.wireup.ui.Screen.SearchScreen
import com.example.wireup.ui.Screen.SettingsScreen
import com.example.wireup.ui.Screen.SplashScreen
import com.example.wireup.ui.Screen.login.AuthenticationScreen
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.ProfileScreenViewMode
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
        composable(NavigationItem.Authentication.route) {
            AuthenticationScreen(navController = navController)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen( navController = navController, viewModel = UserViewModel(
                FirestoreRepository()
            ))
        }
        composable(NavigationItem.FlipFlop.route) {
            FlipFlopScreen( navController = navController)
        }
        composable(NavigationItem.Podcast.route) {
            PodcastScreen( navController = navController)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen( navController = navController, viewModel = UserViewModel(
                FirestoreRepository()
            ))
        }
        composable(NavigationItem.Search.route) {
            SearchScreen( navController = navController)
        }
        composable(NavigationItem.Edit.route) {
            EditProfileScreen( navController = navController)
        }





        composable(NavigationItem.Node.route) {
            NodeScreen(navController = navController, viewModel = UserViewModel(
                FirestoreRepository()
            ))
        }
        composable(NavigationItem.Friends.route) {
            FriendsScreen( navController = navController)
        }
        composable(NavigationItem.Saved.route) {
            SavedScreen( navController = navController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen( navController = navController)
        }
        composable(NavigationItem.About.route) {
            AboutScreen( navController = navController)
        }
        composable(NavigationItem.Account.route) {
            AccountScreen( navController = navController)
        }

//        composable(NavigationItem.ReadMore.route){
//            ReadMore(navController = navController)
//
//        }
        composable(NavigationItem.ReadMore.route + "/{newsId}") { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId").toString()
            ReadMore(navController = navController, newsId)
        }
        composable(NavigationItem.VideoPodcastOpened.route){
            OpenVideoPodcast(navController = navController)
        }
        composable(NavigationItem.AudioPodcastOpened.route){
            OpenAudioPodcast(navController = navController)
        }

        composable(NavigationItem.ProfileViewMode.route + "/{userId}") { entry ->
            ProfileScreenViewMode(navController, entry.arguments?.getString("userId")!!)
        }

        composable(NavigationItem.Liked.route) {
            LikedScreen( navController = navController)
        }

        composable(NavigationItem.FollowNodes.route){
            FollowNodes(navController = navController)
        }

        composable(NavigationItem.Help.route){
            HelpScreen(navController = navController)
        }


    }
}

//