package com.example.wireup.ui.Components

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.wireup.R
import com.example.wireup.model.FFData
import com.example.wireup.model.FlipNews

data class newsx(
    val heading: String,
//    val description: String,
    val report: String,
    val imageUrl: String,
    val time: String
)
@Composable
fun FlipCard(flipNews: FlipNews) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    val news1 = newsx(
        heading = flipNews.heading1.toString(),
//        description = flipNews.description1,
        report = flipNews.report1,
        imageUrl = flipNews.imageUrl1,
        time = flipNews.time.toString()
    )

    val news2 = newsx(
        heading = flipNews.heading2.toString(),
//        description = flipNews.description2,
        report = flipNews.report2,
        imageUrl = flipNews.imageUrl2,
        time = flipNews.time.toString()
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .graphicsLayer {
                    this.rotationY = rotationY
                    cameraDistance = 12f * density
                },
            contentAlignment = Alignment.Center
        ) {
            if (rotationY <= 90f) {
                FrontCardContent(news1)
            } else {
                BackCardContent(news2)
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = if (isFlipped) Color(0xFF4CAF50) else Color(0xFFEF4444)
            ),
            onClick = { isFlipped = !isFlipped }
        ) {
            Text(text = if (isFlipped) "Flip News" else "Flip News")
        }
    }
}


@Composable
fun FrontCardContent(news: newsx) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .clickable { },
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier
        ) {

            Image(
                painter = rememberImagePainter(news.imageUrl),
                contentDescription = "Image in the box",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = news.heading,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
//                Text(
//                    text = news.description,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    fontSize = 14.sp,
//                    maxLines = 4,
//                    overflow = TextOverflow.Ellipsis
//                )
//                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = news.report,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    maxLines = 12,
                    overflow = TextOverflow.Ellipsis
                )
            }


        }
    }

}

@Composable
fun BackCardContent(news: newsx) {
    Card(modifier = Modifier
        .fillMaxWidth(1f)
        .fillMaxHeight(1f)
        .graphicsLayer { scaleX = -1f }
        .clickable { },
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),
    ) {
        Column(
            modifier = Modifier
        ) {
                Image(
                    painter = rememberImagePainter(news.imageUrl),
                    contentDescription = "Image in the box",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(15.dp))
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(
                        text = news.heading,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 18.sp,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
//                    Text(
//                        text = news.description,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        fontSize = 14.sp,
//                        maxLines = 4,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = news.report,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        maxLines = 12,
                        overflow = TextOverflow.Ellipsis
                    )


                }
//                    Spacer(modifier = Modifier.height(6.dp))

            }





        }
    }
