package com.example.wireup.ui.Components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NameInput(
    modifier: Modifier = Modifier,
    nameState: MutableState<String>,
    labelId: String = "Name",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(modifier = modifier,
        valueState = nameState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Text,
        imeAction = imeAction,
        onAction = onAction)


}