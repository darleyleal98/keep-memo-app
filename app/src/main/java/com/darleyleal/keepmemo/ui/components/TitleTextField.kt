package com.darleyleal.keepmemo.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

@Composable
fun TitleTextField(
    title: String,
    maxChar: Int,
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier
) {
    KeepMemoTheme {
        TransparentHintTextField(
            text = title,
            hint = "Title",
            isHintVisible = title.isEmpty(),
            onValueChanged = {
                if (it.length <= maxChar) {
                    viewModel.updateValueTitleField(newValue = it)
                }
            },
            textStyle = TextStyle(
                fontSize = 32.sp,
                color = contentColorFor(
                    backgroundColor = MaterialTheme.colorScheme.surface
                )
            ),
            fontSize = 32.sp,
            singleLine = false
        )
    }
}