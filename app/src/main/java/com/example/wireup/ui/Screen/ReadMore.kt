package com.example.wireup.ui.Screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.model.NewsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadMore( navController: NavHostController) {
    val data = NewsData(
        category ="Philosophy" ,
        trending = true ,
        imageurl = "https://i.abcnewsfe.com/a/3cb8ba6c-ccdb-48de-99cc-e684e5358708/abcnl__NEW_streamingnow_1664457649883_hpMain_16x9.jpg?w=608" ,
        date = "July 10, 2024",
        title = "8 Steps To Help You Stop Overthinking Everything",
        subtitle = "Learn how to manage your thoughts and reduce anxiety."
    )
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
                 text = data.title,
                 fontWeight = FontWeight.Bold,
                 fontSize = 30.sp ,
                 fontFamily = FontFamily.SansSerif
             )
             Spacer(modifier = Modifier.height(10.dp))
             Text(
                 text = data.subtitle,
                 fontSize = 18.sp
             )
             Spacer(modifier = Modifier.height(10.dp))

             Text(
                 text = data.date,
                 textAlign = TextAlign.Start,
                 fontSize = 11.sp,
                 fontWeight = FontWeight(300)
             )

             Spacer(modifier = Modifier.height(10.dp))

             Image(
                 painter = rememberImagePainter(data.imageurl),
                 contentDescription = null,
                 contentScale = ContentScale.FillBounds,
                 modifier = Modifier
                     .fillMaxWidth(1f)
                     .height(190.dp)
//                    .height(200.dp)
             )
             Text(text = "Photo Source : Vinay Rojh " , fontSize = 10.sp , fontWeight = FontWeight.W200 , modifier = Modifier.padding(start = 5.dp))

             Spacer(modifier = Modifier.height(10.dp))

             Text(text="Overthinking can be exhausting and counterproductive. Here are eight steps to help you break the cycle:\n" +
                     "\n" +
                     "1. Recognize the Problem: Acknowledge that you’re overthinking. Awareness is the first step toward change.\n" +
                     "\n" +
                     "2. Focus on What You Can Control: Identify the aspects of the situation that you can influence, and let go of what’s beyond your control.\n" +
                     "\n" +
                     "3. Set Time Limits: Give yourself a set amount of time to think about a problem. After that, move on to something else.\n" +
                     "\n" +
                     "4. Practice Mindfulness: Engage in mindfulness or meditation to stay present and prevent your mind from wandering into overthinking.\n" +
                     "\n" +
                     "5. Challenge Negative Thoughts: Question the validity of your thoughts. Are they based on facts or assumptions? Replace negative thoughts with positive ones.\n" +
                     "\n" +
                     "6. Distract Yourself: Engage in an activity that requires your full attention, like exercising, reading, or spending time with loved ones.\n" +
                     "\n" +
                     "7. Write It Down: Sometimes, writing down your thoughts can help you process them more objectively and relieve the mental burden.\n" +
                     "\n" +
                     "8. Seek Support: Talk to someone you trust about what’s on your mind. They can offer perspective and help you see things more clearly.\n" +
                     "\n" +
                     "By consistently applying these strategies, you can reduce the habit of overthinking and develop a healthier mindset." ,
                 fontSize = 15.sp)

         }

     }

 }
    }