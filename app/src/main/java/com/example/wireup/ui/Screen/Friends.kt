package com.example.wireup.ui.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.R
import com.example.wireup.model.NewsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(navController: NavHostController ) {
    Column {
        TopAppBar(title = {
            Text(
                "Following", fontSize = 18.sp
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


        Friend()
        Friend()
        Friend()
        Friend()
    }



}

@Composable
fun Friend(){
    Card(modifier = Modifier.padding(10.dp),
        colors = CardColors(containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent),) {
        Row(verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceBetween ,
            modifier = Modifier.fillMaxWidth()) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.coin),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .clip(RoundedCornerShape(60.dp))
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = "_ankushdhankhar")
                    Text(text = "Ankush Dhankhar" , fontWeight = FontWeight.W500 , modifier = Modifier.alpha(0.4f))
                }

            }


            Button(
                onClick = { },
                colors = ButtonColors(containerColor = Color.Black , disabledContainerColor = Color.Black , contentColor = Color.White , disabledContentColor = Color.White),
                modifier = Modifier
                    .width(110.dp)
                    .height(40.dp)
                    .padding(5.dp)
                    .clickable { },
                elevation = ButtonDefaults.buttonElevation(5.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Unfollow" , fontSize = 13.sp)
            }
        }
    }
}