package com.example.wireup.ui.Screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Screen.login.LoginScreenViewModel
import com.example.wireup.ui.Screen.viewmodel.LoginScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: LoginScreenViewModel = createViewModel(LocalContext.current as Activity)) {
    Column {
        TopAppBar(title = {
            Text(
                "Settings", fontSize = 18.sp
            )
        }, navigationIcon = {
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

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Text(
                text = "Your Account",
                color = Color.Gray,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
            Row(modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.Account.route)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {

                Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Account")
                Column(modifier = Modifier.padding(start = 10.dp, end = 105.dp)) {
                    Text(text = "Account Details", fontWeight = FontWeight(500), fontSize = 16.sp)
                    Text(
                        text = "Name, Username, Email, etc. ",
                        fontWeight = FontWeight(400),
                        fontSize = 13.sp
                    )
                }
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
            }
            Row(modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.Edit.route)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {

                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit")
                Column(modifier = Modifier.padding(start = 10.dp, end = 190.dp)) {
                    Text(text = "Edit Profile", fontWeight = FontWeight(500), fontSize = 16.sp)
                }
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
            }


            Row(Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.Saved.route)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    painter = painterResource(id = R.drawable.save_wire),
                    contentDescription = "Saved"
                )
                Column(modifier = Modifier.padding(start = 10.dp, end = 226.dp)) {
                    Text(text = "Saved", fontWeight = FontWeight(500), fontSize = 16.sp)
//                    Text(text = "Name , Username , Email , etc. " , fontWeight = FontWeight(400) , fontSize = 13.sp)
                }
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
            }

            Row(modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.Liked.route)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {

                Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Edit")
                Column(modifier = Modifier.padding(start = 10.dp, end = 228.dp)) {
                    Text(text = "Liked", fontWeight = FontWeight(500), fontSize = 16.sp)
//                    Text(text = "Name , Username , Email , etc. " , fontWeight = FontWeight(400) , fontSize = 13.sp)
                }
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
            }

            Row(modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.About.route)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {

                Icon(imageVector = Icons.Outlined.Send, contentDescription = "Edit")
                Column(modifier = Modifier.padding(start = 10.dp, end = 233.dp)) {
                    Text(text = "Help", fontWeight = FontWeight(500), fontSize = 16.sp)
                }
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
            }
            Row(modifier = Modifier
                .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.About.route)
                    }
                ), verticalAlignment = Alignment.CenterVertically) {

                Icon(imageVector = Icons.Outlined.Info, contentDescription = "Edit")
                Column(modifier = Modifier.padding(start = 10.dp, end = 223.dp)) {
                    Text(text = "About", fontWeight = FontWeight(500), fontSize = 16.sp)
                }
                Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()

        Column(modifier = Modifier.fillMaxSize().padding(bottom = 40.dp), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .clickable(
                    onClick = {
                        viewModel.signOut {
                            navController.navigate(NavigationItem.Authentication.route)
                        }
//                        FirebaseAuth
//                            .getInstance()
//                            .signOut()
//                            .run {
//                                navController.navigate(NavigationItem.Authentication.route)
//                            }
                    }
                )
                .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
                .width(150.dp)
                .height(40.dp)
                , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.Center) {

                Icon(
                    imageVector = Icons.Outlined.ExitToApp,
                    contentDescription = "Logout",
                    tint = Color.Red
                )
                Column(modifier = Modifier.padding(start = 5.dp)) {
                    Text(
                        text = "Log Out",
                        fontWeight = FontWeight(400),
                        fontSize = 16.sp,
                        color = Color.Red
                    )
                }
            }
        }

    }

}


fun createViewModel(activity: Activity): LoginScreenViewModel {
    return ViewModelProvider(activity as ViewModelStoreOwner, LoginScreenViewModelFactory(activity)).get(LoginScreenViewModel::class.java)
}