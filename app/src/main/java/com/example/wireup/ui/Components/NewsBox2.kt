package com.example.wireup.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
fun NewsBox2(data: NewsData1, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, bottom = 10.dp)
            .clickable(enabled = true,
                onClick = { navController.navigate(NavigationItem.ReadMore.route + "/${data.id}") }
            ),
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent)
//        border = BorderStroke(width = 1.dp,
//            brush = Brush.linearGradient(colors = listOf(Color.LightGray, Color.LightGray)))
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween ,modifier = Modifier.fillMaxWidth(1f).padding(end = 10.dp)){

            Column(modifier = Modifier
                .padding(start = 15.dp, top = 8.dp, end = 10.dp, bottom = 6.dp)
                .fillMaxWidth(0.65f))
              {
                // Title
                Text(
                    text = data.heading,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 16.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(1.dp))

                // Subtitle
                Text(
                    text = data.description,
                    fontSize = 14.sp,
                            maxLines = 3
                )
                  Text(
                      DateUtil.getDate2(data.time),
                      color = Color.Black,
                      fontSize = 11.sp ,
                      fontWeight = FontWeight.W300

                  )
              }


            // Image
            Image(
                painter = rememberImagePainter(data.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.CenterVertically)
            )

        }

        Divider(modifier = Modifier.alpha(0.5F))
    }

}





