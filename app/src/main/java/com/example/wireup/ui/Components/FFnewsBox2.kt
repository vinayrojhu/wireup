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
import com.example.wireup.R
import com.example.wireup.model.FFData

@Composable
fun FlipCard() {
    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
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
                FrontCardContent()
            } else {
                BackCardContent()
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
fun FrontCardContent() {
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
//                painter = data.item2.image,
                painter = painterResource(id = R.drawable.study_in_australia),
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

@Composable
fun BackCardContent() {
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
//                    painter = data.item1.image,
                    painter = painterResource(id = R.drawable.criminal_laws),
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





        }
    }
