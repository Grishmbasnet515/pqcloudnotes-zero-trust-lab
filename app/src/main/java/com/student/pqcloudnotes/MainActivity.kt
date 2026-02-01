package com.student.pqcloudnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.student.pqcloudnotes.ui.theme.PQCloudNotesTheme
import com.student.pqcloudnotes.ui.navigation.Route
import com.student.pqcloudnotes.ui.screens.LoginScreen
import com.student.pqcloudnotes.ui.screens.PlaceholderScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PQCloudNotesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Route.Login.path,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Route.Login.path) {
                            LoginScreen(
                                onLogin = { navController.navigate(Route.Notes.path) },
                                onRegister = { navController.navigate(Route.Notes.path) }
                            )
                        }
                        composable(Route.Notes.path) {
                            PlaceholderScreen(title = "Notes List")
                        }
                        composable(Route.NoteDetail.path) {
                            PlaceholderScreen(title = "Note Detail")
                        }
                        composable(Route.Settings.path) {
                            PlaceholderScreen(title = "Settings / Security Lab")
                        }
                    }
                }
            }
        }
    }
}
