package com.example.wireup.ui.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Components.NewsBox
import com.example.wireup.ui.Components.NewsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen( navController: NavHostController) {
    val NewsBoxData = NewsData(
        category ="Philosophy" ,
        trending = true ,
        imageurl = "https://i.abcnewsfe.com/a/3cb8ba6c-ccdb-48de-99cc-e684e5358708/abcnl__NEW_streamingnow_1664457649883_hpMain_16x9.jpg?w=608" ,
        date = "2/10/2024",
        title = "8 Steps To Help You Stop Overthinking Everything",
        subtitle = "Learn how to manage your thoughts and reduce anxiety."
    )
    Column(modifier = Modifier) {
        TopAppBar(title = {
            Text(
                "WIREup",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
            )
        }, actions = {
            Row {
                IconButton(onClick = {
                    navController.navigate(NavigationItem.FlipFlop.route)

                }) {
                    Icon(
                        painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        Modifier.padding(8.dp)
                    )
                }


                IconButton(onClick = {
                    navController.navigate(NavigationItem.Search.route)

                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        })
        Divider()

        LazyColumn {
            item { NewsBox(NewsBoxData)
                NewsBox(NewsBoxData)
                NewsBox(NewsBoxData)
                NewsBox(NewsBoxData)
            }


        }


    }
}


