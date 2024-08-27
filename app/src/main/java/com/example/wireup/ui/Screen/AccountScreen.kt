package com.example.wireup.ui.Screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        TopAppBar(title = {
            Text(
                "Your Account", fontSize = 18.sp
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

        Column {
            Card(modifier = Modifier
                .fillMaxWidth()
                ,
                colors = CardDefaults.cardColors(Color.Transparent)) {
                Text(text = "Account Details", color = Color.Gray , fontSize = 13.sp, modifier = Modifier.padding(horizontal = 15.dp , vertical = 5.dp))

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                    .clickable(
                        onClick = {
                        }
                    ), verticalAlignment = Alignment.CenterVertically){

                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "Account")
                    Row(modifier= Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Name" , fontWeight = FontWeight(500) , fontSize = 16.sp)
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
                    }
                }

                Row(modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                    .clickable(
                        onClick = {
                        }
                    ), verticalAlignment = Alignment.CenterVertically){

                    Icon(imageVector = Icons.Outlined.Person, contentDescription = "Account")
                    Row(modifier= Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Unique Id " , fontWeight = FontWeight(500) , fontSize = 16.sp)
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
                    }
                }

                Row(modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp, bottom = 20.dp)
                    .clickable(
                        onClick = {

                        }
                    ), verticalAlignment = Alignment.CenterVertically){

                    Icon(imageVector = Icons.Outlined.Email, contentDescription = "Account")
                    Row(modifier= Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth() , horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Email" , fontWeight = FontWeight(500) , fontSize = 16.sp)
                        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "arrow")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                user?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Delete user data from Firestore
                        val userId = user.uid
                        FirebaseFirestore.getInstance().collection("users").document(userId).delete()
                        Toast.makeText(context, "Account Deleted", Toast.LENGTH_SHORT).show()
                        // Return to login screen
                        navController.popBackStack()
                        navController.navigate(NavigationItem.Authentication.route)
                    } else {
                        Toast.makeText(context, "Error deleting account", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text("Delete Account")
        }


    }
}




//@Composable
//fun deleteAccount() {
//    val user = FirebaseAuth.getInstance().currentUser
//    val context = LocalContext.current
//    user?.delete()?.addOnCompleteListener { task ->
//        if (task.isSuccessful) {
//            // Delete user data from Firestore
//            val userId = user.uid
//            FirebaseFirestore.getInstance().collection("users").document(userId).delete()
//
//            // Return to login screen
//            navController.popBackStack()
//            navController.navigate("login")
//        } else {
//            Toast.makeText(context, "Error deleting account", Toast.LENGTH_SHORT).show()
//        }
//    }
//}