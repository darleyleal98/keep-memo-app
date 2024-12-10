package com.darleyleal.keepmemo.ui.components

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

@Composable
fun DescriptionTextField(
    description: String, viewModel: NoteViewModel,
    modifier: Modifier = Modifier
) {
    KeepMemoTheme {
        TransparentHintTextField(
            text = description,
            hint = "Description",
            isHintVisible = description.isEmpty(),
            onValueChanged = {
                viewModel.updateValueDescriptionField(newValue = it)
            },
            textStyle = TextStyle(
                fontSize = 22.sp,
                color = contentColorFor(
                    backgroundColor = MaterialTheme.colorScheme.surface
                )
            ),
            fontSize = 20.sp,
            singleLine = false,
            modifier = Modifier.imePadding()
        )
    }
}