package com.example.wireup.ui.Screen.login

import android.widget.Space
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.R
import com.example.wireup.ui.Components.EmailInput
import com.example.wireup.ui.Components.PasswordInput

@ExperimentalComposeUiApi
@Composable
fun AuthenticationScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

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

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String, String) -> Unit = { email, password, name ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val name = rememberSaveable { mutableStateOf("") }
    val uniqueId = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value, name.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() && (name.value.trim().isNotEmpty() || !isCreateAccount)

    }
    val modifier = Modifier
        .height(1000.dp)
        .verticalScroll(rememberScrollState())


    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {

        if (isCreateAccount) {
//            OutlinedTextField(
//                value = name.value,
//                onValueChange = { name.value = it },
//                label = { Text("Name") },
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth(0.96f)
//            )

//            OutlinedTextField(
//                value = name.value,
//                onValueChange = { name.value = it },
//                label = { Text("Unique Id") },
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth(0.96f)
//            )
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Name") },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.96f),
                colors = TextFieldDefaults.colors(Color.Black)
            )
            TextField(
                value = uniqueId.value,
                onValueChange = { uniqueId.value = it },
                label = { Text("Unique Id") },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.96f),
                colors = TextFieldDefaults.colors(Color.Transparent)
            )

        }
        EmailInput(
            emailState = email, enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
        )
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading, //Todo change this
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim(), name.value.trim())
            })

        Spacer(modifier = Modifier.height(25.dp))

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim(), name.value.trim())
            keyboardController?.hide()
        }


        Text(text = "or" , fontSize = 13.sp , fontWeight = FontWeight.W500)
        GoogleButton(
            textId = if (isCreateAccount) "Continue with Google" else "Login via Google",
            loading = loading,
            validInputs = valid
        ){
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(horizontal = 15.dp ) , fontSize = 13.sp) else Text("")

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
            modifier = modifier.padding(top = 20.dp, bottom = 1.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.W700,
            color = Color.Black.copy(alpha = 0.7f))

    }

}