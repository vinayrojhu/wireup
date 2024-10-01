package com.example.wireup.ui.Screen

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
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
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Back",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            )

            Divider()

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//                Image(
//                    painter = painterResource(id = R.drawable.podcast_video),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(190.dp)
//                )
                YouTubeVideoPlayer(videoId = getYoutubeVideoId(videoLink))

                Column(modifier = Modifier.padding(start = 10.dp , top = 5.dp)) {
                    Text(
                        text = videoHeading,
                        fontWeight = FontWeight.W900,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 25.sp,
                        textAlign =TextAlign.Start
                    )

//                    Row(modifier = Modifier.padding(start = 5.dp)) {
//                        Text(
//                            text = "2024",
//                            fontWeight = FontWeight.W400,
//                            fontFamily = FontFamily.Monospace,
//                            fontSize = 11.sp,
//                            textAlign = TextAlign.Start
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "U/A 13+",
//                            fontWeight = FontWeight.W400,
//                            fontFamily = FontFamily.Monospace,
//                            fontSize = 11.sp,
//                            textAlign = TextAlign.Start
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "2h 55m",
//                            fontWeight = FontWeight.W400,
//                            fontFamily = FontFamily.Monospace,
//                            fontSize = 11.sp,
//                            textAlign = TextAlign.Start
//                        )
//                    }

                    Button(
                        onClick = { },
                        colors = ButtonColors(containerColor = Color.LightGray , disabledContainerColor = Color.LightGray , contentColor = Color.Black , disabledContentColor = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(end = 8.dp, top = 10.dp),
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Account")
                        Text("Play")
                    }

                    Button(
                        onClick = { },
                        colors = ButtonColors(containerColor = Color.Black , disabledContainerColor = Color.Black , contentColor = Color.White , disabledContentColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(end = 8.dp, top = 10.dp),
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.download_icon),
                            contentDescription = "Download" ,
                            modifier = Modifier.size(25.dp)
                                .padding(end = 5.dp))
//                        Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "Account")
                        Text("Download")
                    }
                    
//                    Text(text = "My technical expertise span programming languages such as Kotlin and JavaScript, and frameworks and libraries like Jetpack Compose and Coroutines." ,
//                        modifier = Modifier.padding(top = 10.dp , end = 5.dp) ,
//                        fontSize = 15.sp)
//
//                    Text(text = "by Vinay Rojh" , fontSize = 12.sp ,
//                        fontWeight = FontWeight(600),
//                        modifier = Modifier.padding(top = 5.dp)
//                    )
                    Row(horizontalArrangement = Arrangement.SpaceAround ,
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(top = 30.dp)){
                        Column {
                            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Account" , modifier = Modifier.size(40.dp))
                            Text(text = "My List",fontSize = 12.sp)
                        }
                        Column {
                            Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription = "Account", modifier = Modifier.size(40.dp))
                            Text(text = "Rate",fontSize = 12.sp)
                        }
                        Column {
                            Icon(imageVector = Icons.Outlined.Share, contentDescription = "Account", modifier = Modifier.size(40.dp))
                            Text(text = "Share",fontSize = 12.sp )
                        }
                            }

                }


            }





    }






}


@Composable
fun YouTubeVideoPlayer(videoId: String) {
    val context = LocalContext.current

    AndroidView(factory = {
        val youTubePlayerView = YouTubePlayerView(context)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0.5f)
            }
        })
        youTubePlayerView
    })
}

fun getYoutubeVideoId(url: String): String {
    return url.split("v=")[1].split("&")[0]
}
