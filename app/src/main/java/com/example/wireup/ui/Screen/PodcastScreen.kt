package com.example.wireup.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.wireup.R
import com.example.wireup.ui.Components.TabView

data class AudioPodcast(
    val title: String,
    val speaker: String,
    val imageUrl: String,
    val duration: Int,
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
                    0 -> VideoScreen()
                    1 -> AudioScreen()
                }





            }
        }







//video
@Composable
fun VideoPostBox(Vpodcast: VideoPodcast) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(Vpodcast.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(630.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Vpodcast.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = Vpodcast.author,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "3 july, 2024",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
                IconButton(onClick = { /* Handle like action */ }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Like"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVideoPostBox() {
    VideoScreen()
}

val videopodcasts = listOf(
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 1 !",
        author = "description 1"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 2 !",
        author = "description 2"),
    VideoPodcast(imageUrl = "https://dpas4li76ctjb.cloudfront.net/wp-content/uploads/2021/11/Picture3-2.png",
        title = "Podcast 3 !",
        author = "description 3")

)



@Composable
fun VideoScreen(){
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(videopodcasts) { Vpodcast ->
            VideoPostBox(Vpodcast)
        }
    }
}














//Audio

@Composable
fun PodcastItem(Apodcast: AudioPodcast) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(Apodcast.imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

            }

            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.align(Alignment.Top)) {
                Text(
                    text = Apodcast.title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Speaker: ${Apodcast.speaker}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Duration :${Apodcast.duration}mins",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
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

@Composable
fun AudioScreen(){
    val pairedPosts = audiopodcasts.chunked(2)
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(audiopodcasts) { Apodcast ->
            PodcastItem(Apodcast)
        }
    }
}