package com.example.wireup.ui.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.model.NewsData1
import com.example.wireup.model.SearchData
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Components.NewsBox3
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.example.wireup.util.DateUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController,
                 modifier: Modifier = Modifier
) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val news1 by viewModel.news1.observeAsState(initial = emptyList())
    var searchQuery = remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()

    Column() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            SearchBar(
                query = searchQuery.value,
                active = false,
                onQueryChange = {
                    searchQuery.value = it
                    coroutineScope.launch {
                        delay(500) // debounce delay
                        Log.d("SearchScreen", "Calling searchNews with query: $it")
                        viewModel.searchNews(it)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                onActiveChange = {  },
                onSearch = {
                    // Handle search action here
                },
                content = {
                    // Add search bar content here, e.g. a cancel button
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                placeholder = {
                    Text(
                        text= "Search",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            Column {
                Text(text = "Politics",
                    Modifier.padding(start = 9.dp, top = 8.dp),
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    news1.filter { it.tags?.contains("all") == true }
                        .forEach { news1 ->
                            NewsCard(news1, navController)
                        }
                }
            }

            if (searchQuery.value.length >= 2) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)) {
                    searchResults.forEach { news ->
                        NewsItem(news, navController)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            }
        }


    }


}
@Composable
fun NewsItem(data: SearchData, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, bottom = 10.dp)
            .clickable(enabled = true,
                onClick = { navController.navigate(NavigationItem.ReadMore.route + "/${data.id}") }
            ),
        colors = CardColors(containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween ,modifier = Modifier
            .fillMaxWidth(1f)
            .padding(end = 10.dp)){

            Column(modifier = Modifier
                .padding(start = 15.dp, top = 8.dp, end = 10.dp, bottom = 6.dp)
                .fillMaxWidth(0.65f))
            {
                // Title
                Text(
                    text = data.heading,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 16.sp
                )
            }
            Image(
                painter = rememberImagePainter(data.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.CenterVertically)
            )
        }
        Divider(modifier = Modifier.alpha(0.5F))
    }
}


@Composable
fun NewsCard(newsItem: NewsData1, navController: NavHostController) {
    Card(
        modifier = Modifier
            .width(290.dp)
            .height(380.dp)
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
                text = newsItem.heading,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate(NavigationItem.ReadMore.route + "/${newsItem.id}") },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text("Read More", color = Color.DarkGray)
            }
        }
    }
}







//@Composable
//fun SearchScreen(navController: NavHostController,
//    modifier: Modifier = Modifier
//) {
//    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
//    val searchQuery = remember { mutableStateOf("") }
//    val searchResults by viewModel.searchResults.observeAsState(emptyList())
//
//
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        SearchBar(
//            value = searchQuery.value,
//            onValueChange = { searchQuery.value = it },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        searchResults.forEach { news ->
//            NewsItem(news)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    }
//}
//
//@Composable
//fun SearchBar(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    TextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text("Search news") },
//        modifier = modifier
//    )
//}
//
//@Composable
//fun NewsItem(news: NewsData1) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        Text(
//            text = news.heading,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Text(
//            text = news.description,
//            fontSize = 16.sp
//        )
//
//        // Add more views as needed
//    }
//}


















//data class VideoPost(
//    val thumbnailUrl: String,
//    val description: String
//)
//data class NewsItem(
//    val title: String,
//    val source: String,
//    val imageUrl: String
//)
//data class Post(
//    val id: String,
//    val userId: String,
//    val postImage: String,
//    val caption: String,
//    val likeCount: Int = 0
//)
//
//
//@Composable
//fun SearchScreen(navController: NavHostController) {
//    Box {
//        FinalSearchPage()
//    }
//
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FinalSearchPage(){
//    Box{
//        var searchData by rememberSaveable {
//            mutableStateOf(emptyList<Post>())
//        }
//        var query by rememberSaveable {
//            mutableStateOf("")
//        }
//        Column {
//            SearchBar(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp, horizontal = 16.dp),
//                query = query,
//                leadingIcon = {
//                    Icon(Icons.Default.Search, contentDescription = "Search")
//                },
//                trailingIcon = {
//                    Icon(
//                        Icons.Default.Close, contentDescription = "Clear",
//                        modifier = Modifier.clickable {
//                            query = ""
//                        }
//                    )
//                },
//                onQueryChange = { q ->
//                    val d = searchData.filter {
//                        it.caption.lowercase().contains(q.lowercase())
//                    }
//                    searchData = d
//                    query = q
//                },
//                placeholder = {
//                    Text(
//                        text= "Search",
//                        fontSize = 16.sp,
//                        color = Color.Gray
//                    )
//                },
//                onSearch = {},
//                active = false,
//                onActiveChange = {}
//            ) {}
//            Text(text = "Trending",
//                Modifier.padding(start = 9.dp, top = 8.dp),
//                fontWeight = FontWeight.W400,
//                fontSize = 16.sp)
//
//            NewsFeed()
//
//            Text(text = "Podcasts",
//                Modifier.padding(start = 9.dp, top = 8.dp, bottom = 5.dp),
//                fontWeight = FontWeight.W400,
//                fontSize = 16.sp)
//
//            PodcastSearch()
//        }
//
//    }
//}
//
//
//@Composable
//fun NewsFeed() {
//    val newsItems = listOf(
//        NewsItem(
//            "Hathras stampede LIVE: NCW chief visits site of incident",
//            "Indic Wire",
//            "https://www.hindustantimes.com/cities/delhi-news/delhi-man-dies-after-falling-from-building-in-south-west-delhi-101663383916959.html"
//        ),
//        NewsItem(
//            "“Manipur Will Reject You”: PM Slams Opposition For “Politicising” Issue",
//            "Indic Wire",
//            "https://www.ndtv.com/india-news/manipur-violence-pm-modi-attacks-opposition-in-parliament-2955629"
//        ),
//        NewsItem(
//            "Amritpal Singh expected to take oath as MP on July 5: Sarabjit Singh Khalsa",
//            "Indic Wire",
//            "https://timesofindia.indiatimes.com/city/chandigarh/amritpal-singh-expected-to-take-oath-as-mp-on-july-5-sarabjit-singh-khalsa/articleshow/98922048.cms"
//        )
//    )
//    LazyRow {
//        items(newsItems) { newsItem ->
//            NewsCard(newsItem)
//        }
//    }
//}
//

//
//
//
//@Composable
//fun PodcastSearch() {
//    val posts = listOf(
//        VideoPost(
//            thumbnailUrl = "https://example.com/thumbnail1.jpg",
//            description = "Video 1 description"
//        ),
//        VideoPost(
//            thumbnailUrl = "https://example.com/thumbnail2.jpg",
//            description = "Video 2 description"
//        ),
//        VideoPost(
//            thumbnailUrl = "https://example.com/thumbnail3.jpg",
//            description = "Video 3 description"
//        ),
//        // Add more posts here
//    )
//
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp)
//    ) {
//        items(posts) { post ->
//            PodcastPostBox(post)
//        }
//    }
//}
//
//
//@Composable
//fun PodcastPostBox(post: VideoPost) {
//    Card(
//        modifier = Modifier
//            .width(150.dp)
//            .height(200.dp)
//            .padding(8.dp),
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Image(
//                painter = rememberAsyncImagePainter(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(post.thumbnailUrl)
//                        .crossfade(true)
//                        .build()
//                ),
//                contentDescription = "Video thumbnail",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = post.description,
//                fontSize = 14.sp,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//    }
//}