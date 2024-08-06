package com.example.wireup.ui.Components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.R

data class NewsItem(
    val heading: String,
    val description: String,
    val image: Painter
)

data class FFData(
    val item1: NewsItem,
    val item2: NewsItem
)

//data class MyData(
//    val items: List<NewsItem>
//)

@Composable
fun FFnewsBox(data: FFData) {
    // State to store the visibility of the heading, image, and description
    val (showContent, setShowContent) = remember { mutableStateOf(true) }

    // Content to be displayed when the heading, image, and description are hidden
    val alternateContent = @Composable {

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = data.item2.image,
                    contentDescription = "Image in the box",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(1f)
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = data.item2.heading,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = data.item2.description,
                            color = Color.Black,
                            fontSize = 14.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .align(Alignment.Bottom)
                        .clickable { }
                ) {
                    Text(
                            text = "Read",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_arrow_96),
                        contentDescription = "read more",
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .weight(1f)
                            .padding(start = 5.dp)
                    )
                }
            }
        }
    }
    Surface(modifier = Modifier
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp)),
        color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)
        ) {
            // Hide the heading, image, and description when the toggle button is clicked
            if (showContent) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = data.item1.image,
                        contentDescription = "Image in the box",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(1f)
                            .background(Color.White)
                            .padding(5.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = data.item1.heading,
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = data.item1.description,
                                color = Color.Black,
                                fontSize = 14.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .align(Alignment.Bottom)
                            .clickable { }
                    ) {
                        Text(
                            text = "Read",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_arrow_96),
                            contentDescription = "read more",
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .weight(1f)
                                .padding(start = 5.dp)
                        )
                    }
                }
            }

            // Conditionally render the alternate content when the heading, image, and description are hidden
            if (!showContent) {
                alternateContent()
            }
            // Toggle button to showand hide the heading, image, and description
            ToggleContentButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                showContent = showContent,
                onToggle = { setShowContent(!showContent) }
            )


        }
    }


}

@Composable
fun ToggleContentButton(
    modifier: Modifier = Modifier,
    showContent: Boolean,
    onToggle: () -> Unit
) {
    // Animate the height of the toggle button
    val height by animateDpAsState(
        if (showContent) 40.dp else 40.dp,
        animationSpec = tween(durationMillis = 3000)
    )

    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .background(if (showContent) Color.Green else Color.Red)
            .clickable(onClick = onToggle)
    ) {
        Text(
            text = "Flip News",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


// how to call
//@Composable
//fun HomeScreen( navController: NavHostController) {
//    val FlipPostdata = FFData(
//        item1 = NewsItem(
//            heading = "Heading 1",
//            description = "Description 1",
//            image = painterResource(id = R.drawable.study_in_australia)
//        ),
//        item2 = NewsItem(
//            heading = "Heading 2",
//            description = "Description 2",
//            image = painterResource(id = R.drawable.criminal_laws)
//        )
//    )
//    Text(text = "yes you can!")
//    Spacer(modifier = Modifier.height(10.dp))
//    FFnewsBox(FlipPostdata)
//}
