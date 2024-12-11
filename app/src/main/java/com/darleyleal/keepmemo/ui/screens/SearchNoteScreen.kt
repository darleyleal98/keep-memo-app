package com.darleyleal.keepmemo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.darleyleal.keepmemo.ui.components.DeleteNoteAlertDialog
import com.darleyleal.keepmemo.ui.components.NoteItemCard
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchNoteScreen(
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier,
    onNavigateToUpdateNoteScreen: (Long) -> Unit
) {
    val text by viewModel.searchtText.collectAsState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    var itemSelected by remember { mutableStateOf<Long?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var removeItem by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val notes by viewModel.notes.collectAsState()

    KeepMemoTheme {
        Scaffold(
            topBar = {
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = text,
                            onQueryChange = { viewModel.updateSearchText(it) },
                            onSearch = { expanded = true },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text(text = "Search") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                if (text.trim().isNotEmpty()) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = null,
                                        modifier = modifier.clickable {
                                            viewModel.updateSearchText("")
                                        }
                                    )
                                }
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    LazyColumn() {
                        items(notes, key = { it.id }) {

                            val containsValue = remember(text) {
                                it.title.contains(text, true) ||
                                        it.description.contains(text, true)
                            }

                            if (containsValue) {
                                ListItem(
                                    headlineContent = { Text(it.title) },
                                    supportingContent = {
                                        Text(
                                            it.description,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    leadingContent = {
                                        Icon(
                                            Icons.Default.EditNote,
                                            contentDescription = null
                                        )
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = Color.Transparent
                                    ),
                                    modifier = modifier.clickable {
                                        itemSelected = it.id
                                        itemSelected?.let { onNavigateToUpdateNoteScreen(it) }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) { contentPadding ->

            Column(modifier.padding(contentPadding)) {
                Spacer(modifier = modifier.padding(bottom = 8.dp))

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(minSize = 200.dp),
                    verticalItemSpacing = 4.dp,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        when {
                            text.isEmpty() -> {
                                items(notes) {
                                    NoteItemCard(
                                        isClicked = {
                                            showBottomSheet = true
                                        }, noteId = { id ->
                                            itemSelected = id
                                        },
                                        note = it
                                    )
                                }
                            }
                        }
                    },
                    modifier = modifier.fillMaxSize()
                )
                when {
                    showBottomSheet -> {
                        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
                            ListItem(
                                headlineContent = { Text(text = "Edit") },
                                leadingContent = {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                }, modifier = modifier.clickable {
                                    itemSelected?.let { onNavigateToUpdateNoteScreen(it) }
                                }
                            )
                            ListItem(
                                headlineContent = { Text(text = "Remove") },
                                leadingContent = {
                                    Icon(Icons.Default.DeleteOutline, contentDescription = null)
                                },
                                modifier = modifier.clickable { removeItem = true }
                            )
                        }
                    }
                }
                when {
                    removeItem -> {
                        DeleteNoteAlertDialog(
                            bottomSheet = { showBottomSheet = it },
                            id = itemSelected,
                            item = { removeItem = it },
                            localContext = context,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}