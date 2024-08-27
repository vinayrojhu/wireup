package com.example.wireup.ui.Components

import android.widget.Space
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.FFData
import org.checkerframework.common.subtyping.qual.Bottom

//data class NewsItem(
//    val heading: String,
//    val description: String,
//    val image: Painter
//)
//
//data class FFData(
//    val item1: NewsItem,
//    val item2: NewsItem
//)

//data class MyData(
//    val items: List<NewsItem>
//)

@Composable
fun FFnewsBox(data: FFData) {

    Column(horizontalAlignment = Alignment.CenterHorizontally ) {
        // State to store the visibility of the heading, image, and description
        val (showContent, setShowContent) = remember { mutableStateOf(true) }

        // Content to be displayed when the heading, image, and description are hidden
        val alternateContent = @Composable {

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    colors = CardColors(containerColor = Color.Transparent,
                        contentColor = Color.Unspecified,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent),
                ) {
                    Column(
                        modifier = Modifier
                    ) {

                        Image(
                            painter = data.item2.image,
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
//                            text = data.item2.heading,
                                text = "Copying Congress Manifesto in 2024 Budget: Rahul Gandhi Accuses Government",
                                color = Color.Black,
                                fontSize = 18.sp,
                                maxLines = 2,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
//                       text = data.item2.description,
                                text = "In a sharp critique of the recently unveiled Union Budget 2024, Congress leader Rahul Gandhi has accused the ruling Bharatiya Janata Party (BJP) government of plagiarizing the Congress party’s election manifesto." +
                                        "In a sharp critique of the recently unveiled Union Budget 2024, Congress leader Rahul Gandhi has accused the ruling Bharatiya Janata Party (BJP) government of plagiarizing the Congress party’s election manifesto." +
                                        "In a sharp critique of the recently unveiled Union Budget 2024, Congress leader Rahul Gandhi has accused the ruling Bharatiya Janata Party (BJP) government of plagiarizing the Congress party’s election manifesto." +
                                        "In a sharp critique of the recently unveiled Union Budget 2024, Congress leader Rahul Gandhi has accused the ruling Bharatiya Janata Party (BJP) government of plagiarizing the Congress party’s election manifesto. ",
                                color = Color.Black,
                                fontSize = 14.sp,
                                maxLines = 12,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
//                    Spacer(modifier = Modifier.height(6.dp))


                    }
                }


        }





        Surface(modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
            color = Color.Transparent) {
            Card(modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f)
                .padding(5.dp)
                .clickable(
                    enabled = true,
                    onClick = {}),
                colors = CardColors(containerColor = Color.Transparent,
                    contentColor = Color.Unspecified,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent),
            ) {
                Column(
                    modifier = Modifier
                ) {
                    // Hide the heading, image, and description when the toggle button is clicked
                    if (showContent) {

                        Image(
                            painter = data.item1.image,
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
//                                text = data.item1.heading,
                                text = "Congress Criticizes Union Budget 2024 as Inadequate for Economic Growth",
                                color = Color.Black,
                                fontSize = 18.sp,
                                maxLines = 2,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
//                                text = data.item1.description,
                                text = "The Indian National Congress has voiced strong criticism against the Union Budget 2024, presented by Finance Minister Nirmala Sitharaman earlier this month." +
                                        "The Indian National Congress has voiced strong criticism against the Union Budget 2024, presented by Finance Minister Nirmala Sitharaman earlier this month." +
                                        "The Indian National Congress has voiced strong criticism against the Union Budget 2024, presented by Finance Minister Nirmala Sitharaman earlier this month." +
                                        "The Indian National Congress has voiced strong criticism against the Union Budget 2024, presented by Finance Minister Nirmala Sitharaman earlier this month.",
                                color = Color.Black,
                                fontSize = 14.sp,
                                maxLines = 12,
                                overflow = TextOverflow.Ellipsis
                            )


                        }
//                    Spacer(modifier = Modifier.height(6.dp))

                    }
                    // Conditionally render the alternate content when the heading, image, and description are hidden
                    if (!showContent) {
                        alternateContent()
                    }




                }
            }



        }


        // Toggle button to showand hide the heading, image, and description
        ToggleContentButton(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp)),
            showContent = showContent,
            onToggle = { setShowContent(!showContent) }
        )
    }



}


@Composable
fun ToggleContentButton(
    modifier: Modifier = Modifier,
    showContent: Boolean,
    onToggle: () -> Unit
){
    // Animate the height of the toggle button
    val height by animateDpAsState(
        if (showContent) 40.dp else 40.dp,
        animationSpec = tween(durationMillis = 3000)
    )

    Box(
        modifier = modifier
            .height(height)
            .fillMaxWidth(0.9f)
            .background(if (showContent) Color(0xFF4CAF50) else Color(0xFFEF4444))
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
