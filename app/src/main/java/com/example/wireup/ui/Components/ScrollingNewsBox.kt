package com.example.wireup.ui.Components

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.NewsData


@Composable
fun ScrollingNewsBox(data: NewsData, navController: NavHostController) {
    Box(
        modifier = Modifier
            .width(335.dp)
            .height(230.dp)
            .padding(start = 13.dp, bottom = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(enabled = true,
                onClick = { navController.navigate(NavigationItem.ReadMore.route) }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.podcast_video),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f)

        )
        Row(modifier = Modifier.padding(10.dp)){
            Text(
                text = "Category",
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier

                    .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp)
            )
        }


        Text(
            text = "This is 1st scrolling news ",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            modifier = Modifier
                .align(Alignment.BottomStart)
//                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp)
        )
    }
    }

