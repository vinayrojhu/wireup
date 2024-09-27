package com.example.wireup.ui.Screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.R
import com.example.wireup.ui.Components.FFnewsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    Column(Modifier.fillMaxHeight()) {
        TopAppBar(title = {
            Text(
                "About", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        )

        Divider()
        
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "About Us ", fontWeight = FontWeight.W600 , fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "An initiative by Indic Wire towards improving facts and providing an unbiased view on current topics ",
                fontSize = 14.sp , textAlign = TextAlign.Start)
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(text="What's so special !!", fontWeight = FontWeight.W600 , fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "The \"FlipFlop\" category presents two articles on the same topic, offering different perspectives. Users can flip between them, encouraging critical thinking and providing reliable, timely, and accurate news.")
            
            Spacer(modifier = Modifier.height(20.dp))

            Text(text="Do visit our website : ", fontWeight = FontWeight.W600 , fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
            LinkText(text = "Indic Wire " , link = "https://www.indicwire.com")

            Spacer(modifier = Modifier.height(20.dp))

            Text(text="For more info , visit : ", fontWeight = FontWeight.W600 , fontSize = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
//            LinkText(text = "Youtube " , link = "https://www.youtube.com/@IndicWire")
//            LinkText(text = "Facebook " , link = "https://www.facebook.com/people/Indic-Wire/100086048454457/")
//            LinkText(text = "Instagram " , link = "https://www.instagram.com/indicwire/")

            SocialMediaLinks(instagramLink ="https://www.instagram.com/indicwire/" , facebookLink ="https://www.facebook.com/people/Indic-Wire/100086048454457/" , youtubeLink ="https://www.youtube.com/@IndicWire" )



        }
        Column(horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Bottom ,
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 15.dp, start = 78.dp)) {
            Text(text = "© 2024 Indic Wire. All rights reserved. ", fontSize = 12.sp , fontWeight = FontWeight(300))
        }
        

    }
}



@Composable
fun LinkText( text : String, link:String) {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = "URL", annotation = link)
        withStyle(style = SpanStyle(color = Color.Black, textDecoration = TextDecoration.Underline)) {
            append(text)
        }
        pop()
    }

    Text(
        text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                annotatedString
                    .getStringAnnotations(tag = "URL", start = 0, end = annotatedString.length)
                    .firstOrNull()
                    ?.let { annotation ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                        context.startActivity(intent)
                    }
            },
        style = MaterialTheme.typography.bodyLarge
    )
}


@Composable
fun SocialMediaLinks(
    instagramLink: String,
    facebookLink: String,
    youtubeLink: String
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconLink(iconRes = R.drawable.youtubeicon, link = youtubeLink, context)
        IconLink(iconRes = R.drawable.instagramicon, link = instagramLink, context)
        IconLink(iconRes = R.drawable.facebookicon, link = facebookLink, context)
    }
}

@Composable
fun IconLink(@DrawableRes iconRes: Int, link: String, context: Context) {
    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            }
    )
}
