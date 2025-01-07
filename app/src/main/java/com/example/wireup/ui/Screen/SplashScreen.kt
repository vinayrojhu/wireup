package com.example.wireup.ui.Screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wireup.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    val fontSize = 38.sp
    val currentFontSizePx = with(LocalDensity.current) { fontSize.toPx() }
    val currentFontSizeDoublePx = currentFontSizePx * 2

    val infiniteTransition = rememberInfiniteTransition(label = "text animation")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizeDoublePx,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "text animate"
    )
    val brush = Brush.linearGradient(
        colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.inversePrimary),
        start = Offset(offset, offset),
        end = Offset(offset + currentFontSizePx, offset + currentFontSizePx),
        tileMode = TileMode.Mirror
    )

    var currentRotation by remember { mutableFloatStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(key1 = Unit) {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ) {
            currentRotation = value
        }
        delay(0L)
          currentOnTimeout()

    }

    LaunchedEffect(key1 = Unit) {
        delay(3000L)
        currentOnTimeout()

    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(36.dp))
            Image(
                painter = painterResource(id = R.drawable.node_iconn),
                modifier = Modifier
                    .size(70.dp)
                    .rotate(rotation.value),
                contentDescription = null ,
                colorFilter = ColorFilter.tint(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black // Change color based on theme
                )
            )
            Spacer(modifier = Modifier.height(400.dp))
            WireLogo()
        }
    }
}


@Composable
fun WireLogo(modifier: Modifier = Modifier) {
    Row {
        Text(text = "Powered by " ,
            modifier = modifier.padding(top = 25.dp, bottom = 1.dp),
            textAlign = TextAlign.Center, fontSize = 15.sp , fontWeight = FontWeight.W300)
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(id = R.drawable.wireup_icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(60.dp)
                .padding(top = 10.dp, bottom = 1.dp)
                .clip(RoundedCornerShape(8.dp)),
            alignment = Alignment.Center ,
            colorFilter = ColorFilter.tint(
                color = if (isSystemInDarkTheme()) Color.White else Color.Black // Change color based on theme
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "INDICwire",
            textAlign = TextAlign.Center,
            modifier = modifier.padding(top = 16.dp, bottom = 1.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}