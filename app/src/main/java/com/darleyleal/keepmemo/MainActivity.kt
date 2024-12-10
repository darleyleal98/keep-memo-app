package com.darleyleal.keepmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.darleyleal.keepmemo.ui.navigation.AppNavigation
import com.darleyleal.keepmemo.ui.theme.KeepMemoTheme
import com.darleyleal.keepmemo.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val addNoteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KeepMemoTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        navController = navController,
                        innerPadding = innerPadding,
                        addNoteViewModel = addNoteViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(
    navController: NavHostController, modifier: Modifier = Modifier,
    innerPadding: PaddingValues, addNoteViewModel: NoteViewModel
) {
    KeepMemoTheme {
        AppNavigation(navController, innerPadding, addNoteViewModel)
    }
}