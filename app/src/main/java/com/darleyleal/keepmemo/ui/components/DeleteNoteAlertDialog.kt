package com.darleyleal.keepmemo.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.darleyleal.keepmemo.R
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

@Composable
fun DeleteNoteAlertDialog(
    bottomSheet: (Boolean) -> Unit,
    id: Long?,
    item: (Boolean) -> Unit,
    localContext: Context,
    viewModel: NoteViewModel
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.title_are_you_sure_about_this))
        },
        text = {
            Text(
                text = stringResource(
                    R.string.message_delete_item
                )
            )
        },
        icon = {
            Icon(Icons.Default.DeleteOutline, null)
        },
        onDismissRequest = {},
        dismissButton = {
            TextButton(onClick = {
                item(false)
                bottomSheet(false)
            }) {
                Text(text = "Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                id?.let { viewModel.delete(it) }
                Toast.makeText(
                    localContext, "This note was deleted!",
                    Toast.LENGTH_SHORT
                ).show()
                item(false)
                bottomSheet(false)
            }) {
                Text(text = "Confirm")
            }
        }
    )
}