package com.example.wireup.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedScreen(navController: NavHostController) {
    Column {
        TopAppBar(title = {
            Text(
                "Liked", fontSize = 18.sp
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

        Row(modifier = Modifier.padding(10.dp)
            , horizontalArrangement = Arrangement.SpaceAround){
            Column(modifier = Modifier.fillMaxWidth(0.5f).padding(10.dp).clickable {  }) {
                Image(painter = painterResource(id = R.drawable.podcastfinal),
                    contentDescription = "",
                    contentScale = ContentScale.Crop ,
                    modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(20.dp)) )
                Text(text = "Audio Podcasts" , modifier = Modifier.padding(start = 10.dp , top = 5.dp) , fontSize = 15.sp)
            }


            Column(modifier = Modifier.fillMaxWidth()
                .padding(10.dp).clickable {  }
            ) {
                Image(painter = painterResource(id = R.drawable.podcast_video),
                    contentDescription = "",
                    contentScale = ContentScale.Crop ,
                    modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(20.dp)) )
                Text(text = "Video Podcasts" , modifier = Modifier.padding(start = 10.dp , top = 5.dp) , fontSize = 15.sp)
            }
        }

    }
}