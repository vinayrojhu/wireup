package com.example.wireup.ui.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.modifier.modifierLocalOf
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
               Column(modifier = Modifier.verticalScroll(state = ScrollState(0))) {
                   Column {
                       Text(text = "World",
                           Modifier.padding(start = 9.dp, top = 8.dp),
                           fontWeight = FontWeight.W400,
                           fontSize = 16.sp)
                       Spacer(modifier = Modifier.height(6.dp))
                       Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                           news1.filter { it.tags?.contains("world") == true }
                               .take(8)
                               .forEach { news1 ->
                                   NewsCard(news1, navController)
                               }
                       }
                   }
                   Column {
                       Text(text = "Politics",
                           Modifier.padding(start = 9.dp, top = 8.dp),
                           fontWeight = FontWeight.W400,
                           fontSize = 16.sp)
                       Spacer(modifier = Modifier.height(6.dp))
                       Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                           news1.filter { it.tags?.contains("politics") == true }
                               .take(8)
                               .forEach { news1 ->
                                   NewsCard2(news1, navController)
                               }
                       }
                   }

               }

            if (searchQuery.value.length >= 2) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)) {
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
        colors = CardColors(containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledContentColor =MaterialTheme.colorScheme.onBackground)
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
            .padding(start = 12.dp, end = 5.dp, bottom = 5.dp, top = 5.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                overflow = TextOverflow.Ellipsis ,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Spacer(modifier = Modifier.height(27.dp))
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




@Composable
fun NewsCard2(newsItem: NewsData1, navController: NavHostController) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(250.dp)
            .padding(start = 12.dp, end = 5.dp, bottom = 10.dp, top = 5.dp)
            .clickable { navController.navigate(NavigationItem.ReadMore.route + "/${newsItem.id}") },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = newsItem.heading,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis ,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}