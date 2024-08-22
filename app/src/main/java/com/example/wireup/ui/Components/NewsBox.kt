package com.example.wireup.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.NewsData
import com.example.wireup.ui.Screen.ReadMore


@Composable
fun NewsBox(data: NewsData , navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
            .padding(10.dp),
        colors = CardColors(containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),
        border = BorderStroke(width = 1.dp,
            brush = Brush.linearGradient(colors = listOf(Color.LightGray, Color.LightGray)))
    ) {
        // Image
        Image(
            painter = rememberImagePainter(data.imageurl),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))

        )
        Box(modifier = Modifier
            .offset(10.dp, 5.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.DarkGray)
            .padding(2.dp)
            .clickable(onClick = { /* handle click */ })){


            Text(
                text = data.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }


        Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 6.dp)) {




            // Title
            Text(
                text = data.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle
            Text(
                text = data.subtitle,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = data.subtitle,
                style = MaterialTheme.typography.bodyLarge
            )

            // Trending indicator
            Box(modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Time ago
                    Text(
                        text = data.date,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(170.dp))

                    Row(modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {navController.navigate(NavigationItem.ReadMore.route)}
                    )) {
                        Text(
                            text = "Read",
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            textAlign = TextAlign.End,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_arrow_96),
                            contentDescription = "read more",
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                }
            }

        }
    }
}

//data class NewsData(
//    val category: String,
//    val trending: Boolean,
//    val imageurl: String,
//    val date: String,
//    val title: String,
//    val subtitle: String
//)

// how to use
//
//@Composable
//fun HomeScreen( navController: NavHostController) {
//    val NewsBoxData = NewsData(
//        category ="Philosophy" ,
//        trending = true ,
//        imageurl = "https://i.abcnewsfe.com/a/3cb8ba6c-ccdb-48de-99cc-e684e5358708/abcnl__NEW_streamingnow_1664457649883_hpMain_16x9.jpg?w=608" ,
//        date = "2/10/2024",
//        title = "8 Steps To Help You Stop Overthinking Everything",
//        subtitle = "Learn how to manage your thoughts and reduce anxiety."
//    )
//    NewsBox(NewsBoxData)
//}

