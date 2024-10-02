package com.example.wireup.ui.Screen

import android.content.Context
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Components.TabView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

data class AudioPodcast(
    val title: String,
    val speaker: String,
    val imageUrl: String,
    val duration: Int
)

data class VideoPodcast(
    val imageUrl: String,
    val title: String,
    val author: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastScreen(navController: NavHostController) {
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
                    0 -> LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        items(videopodcasts) { Vpodcast ->
                            VideoPostBox(Vpodcast, navController)
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







//video
@Composable
fun VideoPostBox(Vpodcast: VideoPodcast, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable(
                onClick = {
                    navController.navigate(NavigationItem.VideoPodcastOpened.route)
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface)
    ) {

        Box(){
            Image(
                painter = rememberAsyncImagePainter(Vpodcast.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Icon(Icons.Filled.PlayArrow, contentDescription = "Friends"
                , modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp) ,
                tint = Color.White)
        }

//            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(top = 14.dp , start = 5.dp , end = 5.dp)) {
                Text(
//                    text = Vpodcast.title,
                    text = "The Motivation Expert : Why You're FAILING To Achieve Your Goals (& What To Do About It!)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start ,
                    maxLines = 3
                )

//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = Vpodcast.author,
//                    fontSize = 14.sp,
//                    textAlign = TextAlign.Start
//                )

                Row(modifier = Modifier ,
                    verticalAlignment = Alignment.CenterVertically ,
                    horizontalArrangement = Arrangement.spacedBy(200.dp)){
                    Text(
                        text = "3 july, 2024",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )

                    val isLiked = remember { mutableStateOf(false) }
                    IconButton(onClick = { isLiked.value = !isLiked.value  }) {
                        Icon(
                            if (isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder ,
                            contentDescription = "Like"
                        )
                    }
                }

        }
        Divider()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewVideoPostBox() {
//    VideoScreen()
//}

val videopodcasts = listOf(
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 1 !  ",
        author = "description 1"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 2 !",
        author = "description 2"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 3 !",
        author = "description 3"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 4 !",
        author = "description 4"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 5 !",
        author = "description 5"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 6 !",
        author = "description 6")

)



//
//@Composable
//fun VideoScreen(){
//    LazyColumn(
//        modifier = Modifier
//            .padding(16.dp)
//    ) {
//        items(videopodcasts) { Vpodcast ->
//            VideoPostBox(Vpodcast)
//        }
//    }
//}














//Audio

@Composable
fun PodcastItem(Apodcast: AudioPodcast , navController: NavHostController ) {



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                onClick = {
                    navController.navigate(NavigationItem.AudioPodcastOpened.route)
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


}


val audiopodcasts = listOf(
    AudioPodcast(
        title = "All About Android 522: Guacamole Comes To Android",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10

    ),
    AudioPodcast(
        title = "All About Android 513: Unlocking Android 12 Secrets",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10
    ),
    AudioPodcast(
        title = "All About Android 506: Android Adjacent",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10
    ),
    AudioPodcast(
        title = "Best External Android Microphones",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10
    ),
    AudioPodcast(
        title = "MacBreak Weekly",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10
    ),
    AudioPodcast(
        title = "Keep Productive: Latest Software Updates",
        speaker = "Vinay Rojh",
        imageUrl = "https://media.wired.com/photos/613bb0daa755c6a4b550bac8/16:9/w_2226,h_1252,c_limit/Gear-Podcast-Hearing-Loss-1279654034.jpg",
        duration = 10
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