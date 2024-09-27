package com.example.wireup.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavHostController) {
    Column(Modifier.fillMaxHeight()) {
        TopAppBar(title = {
            Text(
                "Help", fontSize = 18.sp
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

        Column(modifier = Modifier.padding(horizontal = 10.dp , vertical = 10.dp)) {
            Text(text = "having query, connect with us at :  " , fontSize = 16.sp , fontWeight = FontWeight.W400)
        }

        SocialMediaLinks(instagramLink ="https://www.instagram.com/indicwire/" , facebookLink ="https://www.facebook.com/people/Indic-Wire/100086048454457/" , youtubeLink ="https://www.youtube.com/@IndicWire" )

    }
}

