package com.example.wireup.ui.Components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


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
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = KeyboardActions {
            if (isValidEmail(emailState.value)) {
                passwordFocusRequest.requestFocus()
            } else {
                Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
//@Composable
//fun EmailInput(
//    modifier: Modifier = Modifier,
//    emailState: MutableState<String>,
//    labelId: String = "Email",
//    enabled: Boolean = true,
//    imeAction: ImeAction = ImeAction.Next,
//    onAction: KeyboardActions = KeyboardActions.Default,
//    passwordFocusRequest: FocusRequester
//) {
//    InputField(
//        modifier = modifier,
//        valueState = emailState,
//        labelId = labelId,
//        enabled = enabled,
//        keyboardType = KeyboardType.Email,
//        imeAction = imeAction,
//        onAction = KeyboardActions {
//            passwordFocusRequest.requestFocus()
//        }
//    )
//}
//@Composable
//fun EmailInput(
//    modifier: Modifier = Modifier,
//    emailState: MutableState<String>,
//    labelId: String = "Email",
//    enabled: Boolean = true,
//    imeAction: ImeAction = ImeAction.Next,
//    onAction: KeyboardActions = KeyboardActions.Default
//              ) {
//    InputField(modifier = modifier,
//        valueState = emailState,
//        labelId = labelId,
//        enabled = enabled,
//        keyboardType = KeyboardType.Email,
//        imeAction = imeAction,
//        onAction = onAction)
//
//
//}