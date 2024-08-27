package com.example.wireup.ui.Screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.R
import com.example.wireup.model.FFData
import com.example.wireup.model.FFNewsItem
import com.example.wireup.ui.Components.FFnewsBox
import com.example.wireup.ui.Components.FlipCard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun FlipFlopScreen(navController: NavHostController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Toast.makeText(context, "Swipe for different news", Toast.LENGTH_SHORT).show()
    }
    val FlipPostdata = FFData(
        item1 = FFNewsItem(
            heading = "Heading 1",
            description = "Description 1",
            image = painterResource(id = R.drawable.study_in_australia)
        ),
        item2 = FFNewsItem(
            heading = "Heading 2",
            description = "Description 2",
            image = painterResource(id = R.drawable.criminal_laws)
        )
    )

    Column {
        TopAppBar(
            title = {
                Text("Flip-Flop", fontSize = 18.sp)
            },
            navigationIcon = {
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

        HorizontalPager(
            count = 6, // Set this to the number of cards you want to display
            state = PagerState(1),
            modifier = Modifier.fillMaxSize()
        ) { page ->
//            FFnewsBox(FlipPostdata)
            FlipCard()
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun FlipFlopScreen(navController: NavHostController) {
//        val FlipPostdata = FFData(
//        item1 = FFNewsItem(
//            heading = "Heading 1",
//            description = "Description 1",
//            image = painterResource(id = R.drawable.study_in_australia)
//        ),
//        item2 = FFNewsItem(
//            heading = "Heading 2",
//            description = "Description 2",
//            image = painterResource(id = R.drawable.criminal_laws)
//        )
//    )
//    Column {
//        TopAppBar(title = {
//            Text(
//                "Flip-Flop", fontSize = 18.sp
//            )
//        }, navigationIcon = {
//            IconButton(onClick = {
//                navController.popBackStack()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBack,
//                    contentDescription = "Back",
//                    modifier = Modifier.padding(8.dp)
//                )
//            }
//        },
////            actions = {
////                Row {
////                    IconButton(onClick = { /* no-op */ }) {
////                        Icon(
////                            painter = painterResource(id = R.drawable.global_network),
////                            contentDescription = "globe",
////                            modifier = Modifier.padding(8.dp)
////                        )
////                    }
////                }
////            }
//                )
//
//        Divider()
//
//        LazyColumn {
//            item {
//                FFnewsBox(FlipPostdata)
//                FFnewsBox(FlipPostdata)
//                FFnewsBox(FlipPostdata)
//                FFnewsBox(FlipPostdata)
//            }
//
//
//        }
//    }
//
//
//}