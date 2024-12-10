package com.darleyleal.keepmemo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.darleyleal.keepmemo.R
import com.darleyleal.keepmemo.ui.components.DeleteNoteAlertDialog
import com.darleyleal.keepmemo.ui.components.NoteItemCard
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: NoteViewModel,
    onNavigateToSearchNoteScreen: () -> Unit,
    onNavigateToAddNoteScreen: () -> Unit,
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToUpdateNoteScreen: (Long) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val notes = viewModel.notes.collectAsState().value

    var showBottomSheet by remember { mutableStateOf(false) }
    var itemSelected by remember { mutableStateOf<Long?>(null) }
    var removeItem by remember { mutableStateOf(false) }

    val context = LocalContext.current

    KeepMemoTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    actions = {
                        IconButton(onClick = {
                            onNavigateToSearchNoteScreen()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Row(modifier = modifier.padding(start = 8.dp)) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                modifier = modifier
                                    .padding(end = 2.dp)
                                    .offset(y = (-1).dp)
                            )
                            Text(text = stringResource(R.string.keep_memo))
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onNavigateToAddNoteScreen() },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        ) { contentPadding ->
            Column(modifier = modifier.padding(contentPadding)) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(notes, key = { it.id }) {
                            NoteItemCard(
                                note = it,
                                isClicked = {
                                    showBottomSheet = true
                                },
                                noteId = { id ->
                                    itemSelected = id
                                }
                            )
                        }
                    },
                    modifier = modifier
                        .fillMaxSize()
                )
                when {
                    showBottomSheet -> {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            }
                        ) {
                            ListItem(
                                headlineContent = { Text("Edit") },
                                leadingContent = {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                }, modifier = modifier.clickable {
                                    itemSelected?.let { onNavigateToUpdateNoteScreen(it) }
                                }
                            )
                            ListItem(
                                headlineContent = { Text("Remove") },
                                leadingContent = {
                                    Icon(Icons.Default.DeleteOutline, null)
                                }, modifier = modifier.clickable {
                                    removeItem = true
                                }
                            )
                        }
                    }
                }
                when {
                    removeItem -> {
                        DeleteNoteAlertDialog(
                            bottomSheet = {
                                showBottomSheet = it
                            },
                            id = itemSelected,
                            item = {
                                removeItem = it
                            },
                            localContext = context,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}