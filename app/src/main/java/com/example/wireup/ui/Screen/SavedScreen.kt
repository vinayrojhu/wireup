package com.example.wireup.ui.Screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.wireup.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(navController: NavHostController) {
    Column {
        TopAppBar(title = {
            Text(
                "Saved", fontSize = 18.sp
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
