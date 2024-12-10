package com.darleyleal.keepmemo.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.darleyleal.keepmemo.data.Note
import com.darleyleal.keepmemo.ui.components.DescriptionTextField
import com.darleyleal.keepmemo.ui.components.TitleTextField
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import com.darleyleal.keepmemo.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteScreen(
    note: Note,
    viewModel: NoteViewModel,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onNavigateToHomeScreen: () -> Unit
) {
    KeepMemoTheme {
        val id = note.id
        val title by viewModel.title.collectAsState()
        val description by viewModel.description.collectAsState()
        val maxSizeOfChars = 40

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val scrollState = rememberScrollState()

        val context = LocalContext.current

        LaunchedEffect(note.id) {
            viewModel.updateValueTitleField(note.title)
            viewModel.updateValueDescriptionField(note.description)
        }

        KeepMemoTheme {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                topBar = {
                    LargeTopAppBar(
                        title = {
                            TitleTextField(title, maxSizeOfChars, viewModel)
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                onNavigateToHomeScreen()
                                viewModel.cleanFieldsForm()
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        }, actions = {
                            IconButton(onClick = {
                                if (viewModel.validadeFields(note.title, note.description)) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "This field is required!",
                                            actionLabel = "Dismiss",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                } else {
                                    viewModel.update(id, title, description)
                                    Toast.makeText(
                                        context,
                                        "This note has been inserted successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onNavigateToHomeScreen()
                                }
                            }) {
                                Icon(Icons.Default.Check, contentDescription = null)
                            }
                        }
                    )
                }
            ) { contentPadding ->
                Column(
                    modifier = modifier
                        .padding(
                            top = contentPadding.calculateTopPadding(),
                            start = 16.dp, end = 16.dp
                        )
                        .verticalScroll(scrollState)
                ) {
                    DescriptionTextField(
                        description = description,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}