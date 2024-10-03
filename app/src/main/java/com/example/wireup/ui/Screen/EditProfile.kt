package com.example.wireup.ui.Screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.wireup.model.MUser
import com.example.wireup.repository.FirestoreRepository
import com.example.wireup.ui.Screen.profile.UserViewModel
import com.example.wireup.ui.Screen.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(FirestoreRepository()))
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var uniqueIdState by remember { mutableStateOf("") }
    val userData by viewModel.getUserData().observeAsState()
    val initialUserData = MUser(name = userData?.name.toString(),
        email = userData?.email.toString() , uniqueId = userData?.uniqueId.toString() )
    var updatedUserData = initialUserData.copy()

    val isUniqueIdAvailable by viewModel.isUniqueIdAvailable.observeAsState(true)
    val errorMessage = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(1f)) {
        TopAppBar(title = {
            Text(
                "Edit Profile", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        )

        Divider()



        Surface(modifier = Modifier
            .fillMaxSize(),
            color = Color.Transparent
        ) {
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 20.dp).fillMaxHeight()) {
                Text(text = "Change Details" , textAlign = TextAlign.Start , fontWeight = FontWeight.W700)
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Change UserName") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.padding(4.dp)
                        .fillMaxWidth(0.9f),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent , unfocusedContainerColor = Color.Transparent),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Person icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Change Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.padding(4.dp)
                        .fillMaxWidth(0.9f),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent , unfocusedContainerColor = Color.Transparent),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = "mail icon"
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = uniqueIdState,
                    onValueChange = { uniqueIdState = it },
                    label = { Text("Unique ID") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.padding(4.dp)
                        .fillMaxWidth(0.9f),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent , unfocusedContainerColor = Color.Transparent),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = "face icon"
                        )
                    }
                )

                if (!isUniqueIdAvailable) {
                    Text(
                        text = errorMessage.value,
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Column(modifier = Modifier.padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top){
                    UploadImage(viewModel,navController)
                }

                Column(modifier = Modifier.padding(end=25.dp , bottom = 25.dp).fillMaxSize(), verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End) {

                    Button(onClick = {
                        val userDataToUpdate = updatedUserData.copy()

                        if (name == "") {
                            userData?.name = name
                        }else{
                            userDataToUpdate.name = name
                        }
                        if (email == "") {
                            userData?.email = email
                        }else{
                            userDataToUpdate.email = email
                        }
                        if (uniqueIdState == "") {
                            userData?.uniqueId = uniqueIdState
                        }else{
                            userDataToUpdate.uniqueId = uniqueIdState
                        }

                        viewModel.updateUserData(userDataToUpdate).observe(lifecycleOwner, Observer { isSuccess ->
                            if (isSuccess) {
                                Toast.makeText(context, "User data updated successfully", Toast.LENGTH_SHORT).show()
                                // Update the updatedUserData object with the new values
                                updatedUserData = userDataToUpdate

                                navController.popBackStack()

                            } else {
                                Toast.makeText(context, "Error updating user data", Toast.LENGTH_SHORT).show()
                            }
                        })
                    },
                        colors = ButtonColors(containerColor = Color.Black , disabledContainerColor = Color.Black , contentColor = Color.White , disabledContentColor = Color.White),
                        enabled = isUniqueIdAvailable) {
                        Text("Update Details")
                    }

                    LaunchedEffect(uniqueIdState) {
                        if (uniqueIdState.isNotEmpty()) {
                            viewModel.checkUniqueIdAvailability(uniqueIdState)
                        } else {
                            errorMessage.value = "id already in use"
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UploadImage(viewModel: UserViewModel,navController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imageUrl = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri = data?.data
            imageUri.value = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                launcher.launch(intent)
            },
            colors = ButtonColors(containerColor = Color.LightGray , disabledContainerColor = Color.LightGray , contentColor = Color.Black , disabledContentColor = Color.Black)

        ) {
            Text("                      Update Image                           ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (imageUri.value != null) {
            Image(
                painter = rememberImagePainter(imageUri.value),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape) ,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {
                    isLoading.value = true
                    viewModel.uploadImageToFirebaseStorage(imageUri.value!!).observe(lifecycleOwner, Observer { url ->
                        if (url.isNotEmpty()) {
                            imageUrl.value = url
                            val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                            viewModel.updateUserProfileImage(userId).observe(lifecycleOwner, Observer { isSuccess ->
                                if (isSuccess) {
                                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Error uploading image", Toast.LENGTH_SHORT).show()
                                }
                                isLoading.value = false
                            })
                        } else {
                            Toast.makeText(context, "Error uploading image", Toast.LENGTH_SHORT).show()
                            isLoading.value = false
                        }
                    })
                },
                colors = ButtonColors(containerColor = Color.Black , disabledContainerColor = Color.Gray , contentColor = Color.White, disabledContentColor = Color.Black)
            ) {
                Text("Save Image")
            }

        }

        if (isLoading.value) {
            CircularProgressIndicator()
        }
    }
}

class UserViewModelFactory(private val firestoreRepository: FirestoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(firestoreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}