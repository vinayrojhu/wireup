package com.example.wireup.ui.Screen

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenAudioPodcast( navController : NavHostController) {

    Column{
        TopAppBar(title = {
//            Text(
//                "Back", fontSize = 18.sp
//            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        )


//        Divider()



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))){

            Column {


                AudioPlayerControls()
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp, start = 10.dp)
            ) {
                Text(
                    text = "How to feel confident even in extremely tough situation",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.padding(start = 1.dp)) {
                    Text(
                        text = "2024",
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "U/A 13+",
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "2h 55m",
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Text(
                        text = "Author : ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Ankush Dhankhar , Vinay Rojh  ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }



            }
        }
    }


}




@Composable
fun AudioPlayerControls() {
    val context = LocalContext.current
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf(0) }

    // Initialize the MediaPlayer and set its duration
    LaunchedEffect(Unit) {
        mediaPlayer = MediaPlayer.create(context, R.raw.audiorandom).apply {
            setOnPreparedListener {
                duration = it.duration
            }
        }
    }

    // Release the MediaPlayer when the Composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    // Update the current position of the audio periodically
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            mediaPlayer?.let {
                currentPosition = it.currentPosition
            }
            kotlinx.coroutines.delay(1000)  // Update every 100 milliseconds
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box{
            Image(
                painter = painterResource(id = R.drawable.podcast_video),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            // Play/Pause Button
            Button(
                onClick = {
                    mediaPlayer?.let { player ->
                        if (isPlaying) {
                            player.pause()
                            isPlaying = false
                        } else {
                            player.start()
                            isPlaying = true
                        }
                    }
                },
                modifier = Modifier
//                    .size(60.dp)
                    .align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(Color.Black) ,
                shape = ButtonDefaults.shape
            ) {if (isPlaying) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Pause",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Seek Bar
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = { newValue ->
                mediaPlayer?.seekTo(newValue.toInt())
                currentPosition = newValue.toInt()
            },
            valueRange = 0f..duration.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Time Display
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatTime(currentPosition), fontSize = 14.sp)
            Text(text = formatTime(duration), fontSize = 14.sp)
        }
    }
}

// Helper function to format milliseconds into mm:ss
fun formatTime(milliseconds: Int): String {
    val minutes = (milliseconds / 1000) / 60
    val seconds = (milliseconds / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}



