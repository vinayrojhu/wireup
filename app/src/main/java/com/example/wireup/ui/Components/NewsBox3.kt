package com.example.wireup.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.model.NewsData1
import com.example.wireup.util.DateUtil


@Composable
fun NewsBox3(data: NewsData1, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable(
                enabled = true,
                onClick = { navController.navigate(NavigationItem.ReadMore.route + "/${data.id}") }),
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),
//        border = BorderStroke(width = 1.dp,
//            brush = Brush.linearGradient(colors = listOf(Color.LightGray, Color.LightGray)))
    ) {
        // Image
        Image(
            painter = rememberImagePainter(data.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )


        Column(modifier = Modifier.padding(8.dp)) {

            // Title
            Text(
                text = data.heading,
                fontSize = 15.sp,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onBackground
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
                        DateUtil.getDate2(data.time),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp ,
                        fontWeight = FontWeight.W300
                    )

                }
            }

        }
        Divider(modifier = Modifier.alpha(0.5F))
    }
}