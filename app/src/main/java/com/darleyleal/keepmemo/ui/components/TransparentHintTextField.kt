package com.darleyleal.keepmemo.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    isHintVisible: Boolean,
    onValueChanged: (String) -> Unit,
    textStyle: TextStyle,
    fontSize: TextUnit,
    singleLine: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                onValueChanged(it)
            },
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
            textStyle = textStyle,
            cursorBrush = SolidColor(
                contentColorFor(
                    backgroundColor = MaterialTheme.colorScheme.surface
                )
            )
        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.LightGray, fontSize = fontSize)
        }
    }
}