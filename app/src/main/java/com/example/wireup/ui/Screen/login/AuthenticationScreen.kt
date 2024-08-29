package com.example.wireup.ui.Screen.login

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Components.EmailInput
import com.example.wireup.ui.Components.PasswordInput
import com.example.wireup.ui.Screen.viewmodel.LoginScreenViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

@ExperimentalComposeUiApi
@Composable
fun AuthenticationScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel(factory = LoginScreenViewModelFactory(LocalContext.current as Activity))
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            val name = account.displayName
                            val email = account.email
                            val profileImage = account.photoUrl.toString()
                            val uniqueId = ""

                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "userID" to userId,
                                "profile_image" to profileImage,
                                "uniqueId" to uniqueId
                            )

                            FirebaseFirestore.getInstance().collection("users").document(userId!!).set(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // User data stored successfully, navigate to Home screen
                                        navController.navigate(NavigationItem.Home.route)
                                    } else {
                                        // Handle error
                                    }
                                }
                        } else {
                            // Handle sign-in error
                        }
                    }
            } catch (e: ApiException) {
                // Handle error
            }
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

            Spacer(modifier = Modifier.height(10.dp))
            WireLogo()
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))
            if (showLoginForm.value) UserForm(loading = false, isCreateAccount = false){ email, password, _ ->
                viewModel.signInWithEmailAndPassword(email, password){
                    navController.navigate(NavigationItem.Home.route)
                }
            }
            else {
                UserForm(loading = false, isCreateAccount = true){email, password, name ->
                    viewModel.createUserWithEmailAndPassword(email, password, name) {
                        navController.navigate(NavigationItem.Home.route)
                    }
                }
            }

            GoogleButton(
                textId ="Login via Google",
                loading = false,
                validInputs = true
            ){
//            viewModel.signInWithGoogle()
                val intent = viewModel.signInWithGoogle()
                launcher.launch(intent)
            }

        }

//        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
//            val text = if (showLoginForm.value) "New User ? Sign up" else "Existing User ? Login"
//            Text(text = "New User?")
            val text = if (showLoginForm.value) {
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W400)) {
                        append("New User ? ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Sign up")
                    }
                }
            } else {
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W400)) {
                        append("Existing User ? ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append("Login")
                    }
                }
            }

            Text(text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value

                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,)

        }


    }

}


@ExperimentalComposeUiApi
@Composable
fun UserForm(
    viewModel: LoginScreenViewModel = viewModel(),
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String, String) -> Unit = { email, password, name ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val name = rememberSaveable { mutableStateOf("") }
    val uniqueId = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
//    val passwordFocusRequest = FocusRequester.Default
    val passwordFocusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value, name.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() && (name.value.trim().isNotEmpty() || !isCreateAccount)

    }
    val modifier = Modifier
        .verticalScroll(rememberScrollState())


    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {

        if (isCreateAccount) {
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name") },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.96f),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent , unfocusedContainerColor = Color.Transparent),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person icon"
                    )
                }
            )

        }
//        EmailInput(context = LocalContext.current , emailState = email , passwordFocusRequest = passwordFocusRequest )
        com.example.wireup.ui.Screen.login.EmailInput(
            emailState = email,
            passwordFocusRequest =passwordFocusRequest ,
            context = LocalContext.current
        )


//        old password box

//        PasswordInput(
//            modifier = Modifier.focusRequester(passwordFocusRequest),
//            passwordState = password,
//            labelId = "Password",
//            enabled = !loading,
//            passwordVisibility = passwordVisibility,
//            onAction = KeyboardActions {
//                if (!valid) return@KeyboardActions
//                onDone(email.value.trim(), password.value.trim(), name.value.trim())
//            },
//            focusRequester = passwordFocusRequest
//        )


        //new box password

        com.example.wireup.ui.Screen.login.PasswordInput(
            passwordState = password,
            passwordVisibility =passwordVisibility ,
            focusRequester = passwordFocusRequest
        )

        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp ) , fontSize = 13.sp) else Text("")
//        Spacer(modifier = Modifier.height(25.dp))

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim(), name.value.trim())
            keyboardController?.hide()
        }


        Text(text = "or" , fontSize = 13.sp , fontWeight = FontWeight.W500)


//        GoogleButton(
//            textId = if (isCreateAccount) "Continue with Google" else "Login via Google",
//            loading = loading,
//            validInputs = valid
//        ){
//            viewModel.signInWithGoogle()
//        }




    }
}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 15.dp, end = 20.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }

}

@Composable
fun GoogleButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 15.dp, end = 20.dp)
            .fillMaxWidth(),
        enabled = true,
        shape = CircleShape ,
        colors = ButtonDefaults.buttonColors(Color.Black)
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }

}

@Composable
fun WireLogo(modifier: Modifier = Modifier) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.wireup_icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(60.dp)
                .padding(top = 30.dp, bottom = 1.dp)
                .clip(RoundedCornerShape(8.dp)) ,
            alignment = Alignment.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "WIREup",
            textAlign = TextAlign.Center,
            modifier = modifier.padding(top = 23.dp, bottom = 1.dp),
            fontSize = 35.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black.copy(alpha = 0.7f))

    }

}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    passwordFocusRequest: FocusRequester,
    context: Context
) {
    OutlinedTextField(
        value = emailState.value,
        onValueChange = { emailState.value = it },
        label = { Text(text = labelId) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        modifier = modifier
            .fillMaxWidth(0.94f)
            .focusRequester(passwordFocusRequest),
        singleLine = true,
        enabled = enabled
    )
}



@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    labelId: String = "Password",
    enabled: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    onAction: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester
) {
    val visibilityIcon = if (passwordVisibility.value) {
        Icons.Default.Lock
    } else {
        Icons.Default.Build
    }

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility.value = !passwordVisibility.value
            }) {
                Icon(
                    imageVector = visibilityIcon,
                    contentDescription = if (passwordVisibility.value) "Hide Password" else "Show Password"
                )
            }
        },
        visualTransformation = if (passwordVisibility.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardActions = onAction,
        modifier = modifier
            .fillMaxWidth(0.94f)
            .focusRequester(focusRequester),
        singleLine = true,
        enabled = enabled
    )
}
