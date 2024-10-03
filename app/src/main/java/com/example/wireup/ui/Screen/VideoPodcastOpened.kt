package com.example.wireup.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.wireup.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenVideoPodcast( navController : NavHostController, videoLink: String, videoHeading: String) {


        Column() {
            TopAppBar(title = {
                Text(
                    "Back", fontSize = 18.sp
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            )

            Divider()


            var isPlaying by remember { mutableStateOf(false) } // Track if the play button is clicked

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                // YouTube Video Player remains visible and reacts to `isPlaying`
                YouTubeVideoPlayer(videoId = getYoutubeVideoId(videoLink), isPlaying = isPlaying)

                Column(modifier = Modifier.padding(horizontal =  12.dp , vertical = 12.dp)) {
                    Text(
                        text = videoHeading,
                        fontWeight = FontWeight.W800,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        textAlign =TextAlign.Start
                    )

                    Button(
                        onClick = { isPlaying = !isPlaying// Set the video to play when the button is clicked
                        },
                        colors = ButtonColors(containerColor = Color.LightGray , disabledContainerColor = Color.LightGray , contentColor = Color.Black , disabledContentColor = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(end = 8.dp, top = 10.dp),
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {

                        if (isPlaying){
                            Icon(painter = painterResource(id = R.drawable.pause_icon), contentDescription = "Account", modifier = Modifier.size(20.dp).padding(end = 2.dp))
                        Text("Pause") }
                        else{ Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Account")
                        Text(text = "Play")}
                    }

//                    Row(horizontalArrangement = Arrangement.SpaceAround ,
//                        modifier = Modifier
//                            .fillMaxWidth(1f)
//                            .padding(top = 30.dp)){
//                        Column {
//                            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Account" , modifier = Modifier.size(40.dp))
//                            Text(text = "My List",fontSize = 12.sp)
//                        }
//                        Column {
//                            Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription = "Account", modifier = Modifier.size(40.dp))
//                            Text(text = "Rate",fontSize = 12.sp)
//                        }
//                        Column {
//                            Icon(imageVector = Icons.Outlined.Share, contentDescription = "Account", modifier = Modifier.size(40.dp))
//                            Text(text = "Share",fontSize = 12.sp )
//                        }
//                            }

                }


            }





    }






}

@Composable
fun YouTubeVideoPlayer(videoId: String, isPlaying: Boolean) {
    val context = LocalContext.current
    // Keep a reference to YouTubePlayer instance in remember to persist across recompositions
    val youTubePlayerInstance = remember { mutableStateOf<YouTubePlayer?>(null) }

    AndroidView(
        factory = {
            // Create the YouTubePlayerView
            val youTubePlayerView = YouTubePlayerView(context)
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    // Assign the YouTubePlayer instance
                    youTubePlayerInstance.value = youTubePlayer
                    // Load the video, but don't auto-play
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
            youTubePlayerView
        },
        update = {
            // Control the YouTubePlayer based on `isPlaying` only after it's ready
            youTubePlayerInstance.value?.let { player ->
                if (isPlaying) {
                    player.play() // Play the video
                } else {
                    player.pause() // Pause the video
                }
            }
        }
    )
}

fun getYoutubeVideoId(url: String): String {
    return url.split("v=")[1].split("&")[0]
}
