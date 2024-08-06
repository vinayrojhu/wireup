package com.example.wireup.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

data class VideoPost(
    val thumbnailUrl: String,
    val description: String
)
data class NewsItem(
    val title: String,
    val source: String,
    val imageUrl: String
)
data class Post(
    val id: String,
    val userId: String,
    val postImage: String,
    val caption: String,
    val likeCount: Int = 0
)


@Composable
fun SearchScreen(navController: NavHostController) {
    Box {
        FinalSearchPage()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalSearchPage(){
    Box{
        var searchData by rememberSaveable {
            mutableStateOf(emptyList<Post>())
        }
        var query by rememberSaveable {
            mutableStateOf("")
        }
        Column {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                query = query,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.Close, contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            query = ""
                        }
                    )
                },
                onQueryChange = { q ->
                    val d = searchData.filter {
                        it.caption.lowercase().contains(q.lowercase())
                    }
                    searchData = d
                    query = q
                },
                placeholder = {
                    Text(
                        text= "Search",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                },
                onSearch = {},
                active = false,
                onActiveChange = {}
            ) {}
            Text(text = "Trending",
                Modifier.padding(start = 9.dp, top = 8.dp),
                fontWeight = FontWeight.W400,
                fontSize = 16.sp)

            NewsFeed()

            Text(text = "Podcasts",
                Modifier.padding(start = 9.dp, top = 8.dp, bottom = 5.dp),
                fontWeight = FontWeight.W400,
                fontSize = 16.sp)

            PodcastSearch()
        }

    }
}


@Composable
fun NewsFeed() {
    val newsItems = listOf(
        NewsItem(
            "Hathras stampede LIVE: NCW chief visits site of incident",
            "Indic Wire",
            "https://www.hindustantimes.com/cities/delhi-news/delhi-man-dies-after-falling-from-building-in-south-west-delhi-101663383916959.html"
        ),
        NewsItem(
            "“Manipur Will Reject You”: PM Slams Opposition For “Politicising” Issue",
            "Indic Wire",
            "https://www.ndtv.com/india-news/manipur-violence-pm-modi-attacks-opposition-in-parliament-2955629"
        ),
        NewsItem(
            "Amritpal Singh expected to take oath as MP on July 5: Sarabjit Singh Khalsa",
            "Indic Wire",
            "https://timesofindia.indiatimes.com/city/chandigarh/amritpal-singh-expected-to-take-oath-as-mp-on-july-5-sarabjit-singh-khalsa/articleshow/98922048.cms"
        )
    )
    LazyRow {
        items(newsItems) { newsItem ->
            NewsCard(newsItem)
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .width(290.dp)
            .height(405.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(newsItem.imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "News Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = newsItem.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsItem.source,
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Navigate to article details */ },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text("Read More", color = Color.DarkGray)
            }
        }
    }
}



@Composable
fun PodcastSearch() {
    val posts = listOf(
        VideoPost(
            thumbnailUrl = "https://example.com/thumbnail1.jpg",
            description = "Video 1 description"
        ),
        VideoPost(
            thumbnailUrl = "https://example.com/thumbnail2.jpg",
            description = "Video 2 description"
        ),
        VideoPost(
            thumbnailUrl = "https://example.com/thumbnail3.jpg",
            description = "Video 3 description"
        ),
        // Add more posts here
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(posts) { post ->
            PodcastPostBox(post)
        }
    }
}


@Composable
fun PodcastPostBox(post: VideoPost) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.thumbnailUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "Video thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}