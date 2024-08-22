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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.R
import com.example.wireup.model.NewsData


@Composable
fun NewsBox2(data: NewsData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp , bottom = 10.dp, end = 10.dp)
            .clickable { },
        colors = CardColors(containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),
//        border = BorderStroke(width = 1.dp,
//            brush = Brush.linearGradient(colors = listOf(Color.LightGray, Color.LightGray)))
    ) {
        Row {
            // Image
            Image(
                painter = rememberImagePainter(data.imageurl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(124.dp)

            )

//            Box(modifier = Modifier
//                .offset(10.dp, 5.dp)
//                .clip(RoundedCornerShape(4.dp))
//                .background(Color.DarkGray)
//                .padding(2.dp)
//                .clickable(onClick = { /* handle click */ })){
//
//
//                Text(
//                    text = data.category,
//                    color = MaterialTheme.colorScheme.onPrimary ,
//                    fontSize = 10.sp
//                )
//            }

            Column(modifier = Modifier
                .padding(start = 10.dp, top = 8.dp, end = 16.dp, bottom = 6.dp))
              {

                // Title
                Text(
                    text = data.title,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(1.dp))

                // Subtitle
                Text(
                    text = data.subtitle,
                    fontSize = 14.sp
                )


                    Text(
                        text = data.date,
                        textAlign = TextAlign.Start,
                        fontSize = 11.sp ,
                        fontWeight = FontWeight(300)
                    )
              }





        }
    }

}





