package com.example.wireup.ui.Screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.R
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.example.wireup.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadMore(navController: NavHostController, newsId: String) {

    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val newsData by viewModel.getNewsData(newsId).observeAsState()

    val time = newsData?.let { DateUtil.getDate2(it.time) }

    LaunchedEffect(Unit) {
        viewModel.getNewsData(newsId)
    }
 Column (modifier = Modifier
     .fillMaxSize()
      ){
     TopAppBar(title = {
         Text(
             "Back", fontSize = 18.sp
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
     },
         actions = {
             Row {
                 IconButton(onClick = { }) {
                     Icon(
                         painterResource(id = R.drawable.save_wire),
                         contentDescription = null,
                         Modifier.padding(8.dp)
                     )
                 }

                 IconButton(onClick = {} ) {
                     Icon(imageVector = Icons.Outlined.Share, contentDescription = "Search")
                 }
             }
         }
     )

     Column(modifier = Modifier
         .fillMaxWidth()
         .padding(bottom = 5.dp , start = 5.dp , end = 5.dp , top = 0.dp)
         .verticalScroll(rememberScrollState())) {
         Column(modifier = Modifier.padding(bottom = 10.dp , start = 10.dp , end = 10.dp , top = 0.dp)) {

             Text(
                 text = newsData?.heading.toString(),
                 fontWeight = FontWeight.Bold,
                 fontSize = 30.sp ,
                 fontFamily = FontFamily.SansSerif,
                 color = MaterialTheme.colorScheme.onBackground
             )
             Spacer(modifier = Modifier.height(10.dp))
             Text(
                 text = newsData?.description.toString(),
                 fontSize = 18.sp,
                 color = MaterialTheme.colorScheme.onBackground
             )
             Spacer(modifier = Modifier.height(10.dp))

             Text(
                 text = time.toString(),
                 textAlign = TextAlign.Start,
                 fontSize = 11.sp,
                 fontWeight = FontWeight(300),
                 color = MaterialTheme.colorScheme.onBackground
             )

             Spacer(modifier = Modifier.height(10.dp))

             Image(
                 painter = rememberImagePainter(newsData?.imageUrl),
                 contentDescription = null,
                 contentScale = ContentScale.FillBounds,
                 modifier = Modifier
                     .fillMaxWidth(1f)
                     .height(190.dp)
//                    .height(200.dp)
             )

             Spacer(modifier = Modifier.height(10.dp))

             Text(text= newsData?.report.toString(),
                 fontSize = 15.sp,
                 color = MaterialTheme.colorScheme.onBackground)

         }

     }

 }
    }