package com.darleyleal.keepmemo.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.darleyleal.keepmemo.ui.screens.AddNoteScreen
import com.darleyleal.keepmemo.ui.screens.HomeScreen
import com.darleyleal.keepmemo.ui.screens.SearchNoteScreen
import com.darleyleal.keepmemo.ui.screens.UpdateNoteScreen
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: NoteViewModel
) {
    var noteId = 0L
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.name
    ) {
        composable(route = Routes.HomeScreen.name) {
            HomeScreen(
                viewModel = viewModel,
                innerPadding = innerPadding,
                onNavigateToAddNoteScreen = {
                    navController.navigate(Routes.AddNoteScreen.name)
                },
                onNavigateToUpdateNoteScreen = {
                    noteId = it
                    navController.navigate(Routes.UpdateNoteScreen.name)
                },
                onNavigateToHomeScreen = {
                    navController.navigate(Routes.HomeScreen.name)
                },
                onNavigateToSearchNoteScreen = {
                    navController.navigate(Routes.SearchNoteScreen.name)
                }
            )
        }
        composable(route = Routes.AddNoteScreen.name) {
            AddNoteScreen(
                viewModel = viewModel,
                onNavigateToHomeScreen = {
                    navController.navigate(Routes.HomeScreen.name)
                }
            )
        }

        composable(route = Routes.SearchNoteScreen.name) {
            SearchNoteScreen(viewModel, onNavigateToUpdateNoteScreen = {
                noteId = it
                navController.navigate(Routes.UpdateNoteScreen.name)
            })
        }

        composable(route = Routes.UpdateNoteScreen.name) {

            viewModel.getNoteById(noteId)
            val note = viewModel.note.collectAsState().value

            note?.let {
                UpdateNoteScreen(note = it, viewModel = viewModel, onNavigateToHomeScreen = {
                    navController.navigateUp()
                })
            }
        }
    }
}