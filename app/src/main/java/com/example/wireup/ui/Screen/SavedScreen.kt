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
        Text(text = "Saved Screen")
        Spacer(modifier = Modifier.height(10.dp))
        
        AudioPlayer(imageResource = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0va-Y5gI_CwfAIlHes8kpl0pa_H5A1eAwYlRLivX-LiFpwQHA4VC0adh1Ng1ZDvLETII&usqp=CAU")


    }
}





@Composable
fun AudioPlayer(imageResource: String) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var mediaPlayer: MediaPlayer? = null
    val audioResource = R.raw.audiorandom// Replace with your audio resource
    val imageResource = R.drawable.indic_logo// Replace with your image resource

    if (isPlaying) {
        mediaPlayer = MediaPlayer.create(LocalContext.current, audioResource)
        mediaPlayer?.start()
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (mediaPlayer?.isPlaying == true) {
                progress = mediaPlayer!!.currentPosition.toFloat() / mediaPlayer!!.duration
                delay(100) // Update every 100 milliseconds
            }
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
        }
    }

    Box(modifier = Modifier.height(80.dp).fillMaxWidth()) {
        Row {
            Image(
                painter = rememberImagePainter(
                    data = imageResource
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { isPlaying = !isPlaying }
                ) {
                    Text(text = if (isPlaying) "Pause" else "Play")
                }

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}
