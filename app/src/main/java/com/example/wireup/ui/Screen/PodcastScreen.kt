package com.example.wireup.ui.Screen

import android.content.Context
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.NewsBox3
import com.example.wireup.ui.Components.TabView
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

data class AudioPodcast(
    val title: String,
    val speaker: String,
    val imageUrl: String,
    val duration: Int,
    val url: String
)

data class VideoPodcast(
    val id: String,
    val imageUrl: String,
    val title: String,
    val videoLink: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastScreen(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val video1 by viewModel.video.observeAsState(initial = emptyList())

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    Column {
        TopAppBar(title = {
            Text(
                "Podcast", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
            actions = {
                Row {
                    IconButton(onClick = { /* no-op */ }) {
                        Icon(painter = painterResource(id = R.drawable.global_network),
                            contentDescription = "globe",
                            modifier = Modifier.padding(8.dp))
                    }
                }
            })



                TabView(
                    imageWithText = listOf(
                        "VideoPodcast" to painterResource(id = R.drawable.podcast_profile_icon),
                        "AudioPodcast" to painterResource(id = R.drawable.podcastfinal),
                    )
                ) {
                    selectedTabIndex = it
                }
                when (selectedTabIndex) {
                    0 -> Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        video1.forEach { data ->
                                VideoPostBox(Vpodcast = data, navController)
                            }
                    }
                    1 -> LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        items(audiopodcasts) { Apodcast ->
                            PodcastItem(Apodcast, navController )
                        }
                    }
                }
     }
}

fun getVideoIdFromUrl(url: String): String? {
    val pattern = "v=([^&]+)".toRegex()
    val matchResult = pattern.find(url)
    return matchResult?.groups?.get(1)?.value
}

@Composable
fun YouTubeVideoThumbnail(url: String) {
    val videoId = getVideoIdFromUrl(url)
    if (videoId != null) {
        val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"
        AsyncImage(model = thumbnailUrl,
            contentDescription = "YouTube video thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)))
    } else {
        Text("Invalid YouTube video URL")
    }
}





//video
@Composable
fun VideoPostBox(Vpodcast: VideoPodcast, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable(
                onClick = {
                    val videoLinkEncoded = Uri.encode(Vpodcast.videoLink)
                    val headingEncoded = Uri.encode(Vpodcast.title)
                    navController.navigate(NavigationItem.VideoPodcastOpened.route + "/$videoLinkEncoded/$headingEncoded")
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface)
    ) {

        Box(){
//            Image(
//                painter = rememberAsyncImagePainter(Vpodcast.videoLink),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth(1f)
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(16.dp))
//            )
            YouTubeVideoThumbnail(Vpodcast.videoLink)
            Icon(Icons.Filled.PlayArrow, contentDescription = "Play"
                , modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp) ,
                tint = Color.White)
        }

            Column(modifier = Modifier.padding(top = 14.dp , start = 5.dp , end = 5.dp)) {
                Text(
                    text = Vpodcast.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start ,
                    maxLines = 3
                )

//                Row(modifier = Modifier ,
//                    verticalAlignment = Alignment.CenterVertically ,
//                    horizontalArrangement = Arrangement.spacedBy(200.dp)){
//                    Text(
//                        text = "3 july, 2024",
//                        fontSize = 14.sp,
//                        textAlign = TextAlign.Start
//                    )
//
//                    val isLiked = remember { mutableStateOf(false) }
//                    IconButton(onClick = { isLiked.value = !isLiked.value  }) {
//                        Icon(
//                            if (isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder ,
//                            contentDescription = "Like"
//                        )
//                    }
//                }

        }
        Divider()
    }
}


//Audio

@Composable
fun PodcastItem(Apodcast: AudioPodcast , navController: NavHostController ) {

    var isPlaying by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                onClick = {
                    isPlaying = !isPlaying
//                    AudioPodcastPlayer(url = url)
//                    navController.navigate(NavigationItem.AudioPodcastOpened.route)
                }
            ), 
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(Apodcast.imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )

            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 5.dp)
            ) {
                Text(
                    text = Apodcast.title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Column {
                        Text(
                            text = "by ${Apodcast.speaker}",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${Apodcast.duration} min",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column {
                        val isLiked = remember { mutableStateOf(false) }
                        IconButton(onClick = { isLiked.value = !isLiked.value  }) {
                            Icon(
                                if (isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder ,
                                contentDescription = "Like"
                            )
                        }
                    }

                }

            }
        }
    }

    // Conditionally show the audio player when the podcast box is clicked
    if (isPlaying) {
        // AudioPodcastPlayer is invoked here in the same @Composable context
        AudioPodcastPlayer("https://www.iheart.com/podcast/1119-stuff-you-should-know-26940277/")
    }
}

@Composable
fun AudioPodcastPlayer(url: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(
        AndroidView(factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

val audiopodcasts = listOf(
    AudioPodcast(
        title = "All About Android 522: Guacamole Comes To Android",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10 ,
        url="https://www.iheart.com/podcast/1119-stuff-you-should-know-26940277/"
    ),
    AudioPodcast(
        title = "All About Android 513: Unlocking Android 12 Secrets",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10 ,
        url = ""
    ),
    AudioPodcast(
        title = "All About Android 506: Android Adjacent",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10 ,
        url = ""
    ),
    AudioPodcast(
        title = "Best External Android Microphones",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10 ,
        url = ""
    ),
    AudioPodcast(
        title = "MacBreak Weekly",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10 ,
        url = ""
    ),
    AudioPodcast(
        title = "Keep Productive: Latest Software Updates",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10 ,
        url = ""
    )
)

//@Composable
//fun AudioScreen(){
//    val pairedPosts = audiopodcasts.chunked(2)
//    LazyColumn(
//        modifier = Modifier
//            .padding(16.dp)
//    ) {
//        items(audiopodcasts) { Apodcast ->
//            PodcastItem(Apodcast)
//        }
//    }
//}