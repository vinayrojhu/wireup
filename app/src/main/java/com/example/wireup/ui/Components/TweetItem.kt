package com.example.wireup.ui.Components

import android.net.Uri
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.R
import com.example.wireup.model.MUser
import com.example.wireup.ui.Screen.Tweet
import com.example.wireup.util.DateUtil.getDateTime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

@Composable
fun TweetItem(
    tweet: Tweet,
    onCommentClick: () -> Unit,
    navController: NavHostController,
    onItemClick: () -> Unit,
) {
    var isLiked by rememberSaveable {
        mutableStateOf(false)
    }
    var isRetweeted by rememberSaveable {
        mutableStateOf(false)
    }
    var isBookmarked by rememberSaveable {
        mutableStateOf(false)
    }
    var likes by rememberSaveable {
        mutableIntStateOf(tweet.likeCount)
    }
    var retweets by rememberSaveable {
        mutableIntStateOf(tweet.retweetCount)
    }
    var bookmarks by rememberSaveable {
        mutableIntStateOf(tweet.bookmarkCount)
    }
    // yo demouser woh h jo comment krega
//    val demoUser = WUser(
//        id = FirebaseAuth.getInstance().currentUser?.email.toString(),
//        name = "Vinay",
//        profileImage = "https://static.vecteezy.com/system/resources/thumbnails/005/545/335/small/user-sign-icon-person-symbol-human-avatar-isolated-on-white-backogrund-vector.jpg",
//        bio = "Android developer | Nature Lover",
//        links = listOf("www.indicwire.com"),
//        followerIds = listOf("12341", "12342", "12343", "12344", "12345", "12346", "12347"),
//        followingIds = listOf("12342", "12345", "12344", "12346", "12347"),
//        postIds = listOf(
//            "123451",
//            "123452",
//            "123453",
//            "123454",
//            "123455",
//        ),
//        storyIds = listOf("1234561", "1234562", "1234563", "1234564", "1234565"),
//    )
    val user = MUser(id = "23323", name = "vinay", email = "sdfsd@fss.in", profileImage = "https://static.vecteezy.com/system/resources/thumbnails/005/545/335/small/user-sign-icon-person-symbol-human-avatar-isolated-on-white-backogrund-vector.jpg")
    val userimage = remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        FirebaseStorage.getInstance().reference.child("users/${FirebaseAuth.getInstance().currentUser?.uid}/profile_image").downloadUrl.addOnSuccessListener { uri ->
            userimage.value = uri
        }
    }
    Row(
        Modifier
            .fillMaxWidth(1f)
            .padding(start = 35.dp , end = 8.dp)
            .clickable {
                onItemClick()
            }
    ) {
        CircularImage(imageUri =  userimage, imageSize = 30.dp,
            navController = navController ,
            userUuid = FirebaseAuth.getInstance().currentUser?.uid.toString())
        Spacer(Modifier.width(8.dp))
        Column(Modifier.fillMaxWidth(1f)) {
            Row(Modifier.fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(user.name, fontWeight = FontWeight.W600, fontSize = 13.sp )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = getDateTime(tweet.timeStamp),
                    textAlign = TextAlign.End,
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(tweet.description, fontSize = 13.sp, fontWeight = FontWeight.W400)
            Spacer(Modifier.height(8.dp))
//            TweetActionRow(isLiked, isRetweeted, isBookmarked,
//                likes,
//                retweets,
//                bookmarks,
//                onLikeClicked = {
//                    if (it)
//                        likes++
//                    else
//                        likes--
//                    isLiked = it
//                }, onRetweetClicked = {
//                    if (it)
//                        retweets++
//                    else
//                        retweets--
//                    isRetweeted = it
//                }, onBookmarkClicked = {
//                    if (it)
//                        bookmarks++
//                    else
//                        bookmarks--
//                    isBookmarked = it
//                }) {
//                onCommentClick()
//            }
        }
    }
}

@Composable
fun NumberIcon(
    count: Int,
    initialState: Boolean,
    enableIcon: Painter,
    enableTint: Color = MaterialTheme.colorScheme.onBackground,
    disableIcon: Painter,
    disableTint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ToggleIconButton(
            enableIcon = enableIcon,
            disableIcon = disableIcon,
            enableTint = enableTint,
            initialState = initialState,
            disableTint = disableTint
        ) {
            onClick(it)
        }
        Spacer(modifier = Modifier.width(2.dp))
        Text(count.toString(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TweetActionRow(
    isLiked: Boolean,
    isRetweeted: Boolean,
    isBookmarked: Boolean,
    likeCount: Int,
    retweetCount: Int,
    bookmarkCount: Int,
    onLikeClicked: (Boolean) -> Unit,
    onRetweetClicked: (Boolean) -> Unit,
    onBookmarkClicked: (Boolean) -> Unit,
    onCommentClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        IconButton(onClick = {
            onCommentClick()
        }) {
            Icon(
                painterResource(id = R.drawable.global_network),
                contentDescription = "Comment"
            )
        }
        NumberIcon(
            count = retweetCount,
            initialState = isRetweeted,
            painterResource(id = R.drawable.node_icon6),
            Color.Blue,
            painterResource(R.drawable.node_icon6)
        ) {
            onRetweetClicked(it)
        }
        NumberIcon(
            count = likeCount,
            initialState = isLiked,
            rememberVectorPainter(image = Icons.Default.Favorite),
            Color.Red,
            rememberVectorPainter(image = Icons.Default.FavoriteBorder),
        ) {
            onLikeClicked(it)
        }
        NumberIcon(
            count = bookmarkCount,
            initialState = isBookmarked,
            enableIcon = painterResource(id = R.drawable.save_wire),
            disableIcon = painterResource(id = R.drawable.save_wire)
        ) {
            onBookmarkClicked(it)
        }
        IconButton(onClick = {
            //share tweet link
        }) {
            Icon(
                Icons.Default.Share,
                modifier = Modifier.size(18.dp) ,
                contentDescription = "Share"
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TweetItemPreview() {
//    JetPackComposeBasicTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            TweetItem(
//                Tweet(
//                    description = "Incorporate and convert Java code into #Kotlin using #Android Studio, and learn Kotlin language conventions along the way. You’ll also learn how to write Kotlin code to make it callable from Java code.",
//                    userId = DemoData.demoUser.id,
//                    comments = listOf("very nice"),
//                    likeCount = 203,
//                    retweetCount = 50,
//                    bookmarkCount = 135,
//                    imageUrl = DemoData.urls.subList(0, 3)
//                ),
//                onCommentClick = {}
//            ) {
//            }
//        }
//    }
//}