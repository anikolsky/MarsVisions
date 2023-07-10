package com.omtorney.marsvisions

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.omtorney.marsvisions.presentation.detail_screen.DetailScreen
import com.omtorney.marsvisions.presentation.main_screen.MainScreen
import com.omtorney.marsvisions.presentation.main_screen.MainViewModel
import com.omtorney.marsvisions.presentation.theme.MarsVisionsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarsVisionsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main"
                    ) {
                        composable(route = "main") {
                            val viewModel: MainViewModel = hiltViewModel()
                            val state = viewModel.state
                            MainScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                onClick = { url ->
                                    navController.navigate(route = "detail/${Uri.encode(url)}")
                                }
                            )
                        }
                        composable(
                            route = "detail/{url}",
                            arguments = listOf(navArgument(name = "url") { NavType.StringType })
                        ) { backStackEntry ->
                            val url = Uri.decode(backStackEntry.arguments?.getString("url"))
                            DetailScreen(url = url ?: "")
                        }
                    }
                }
            }
        }
    }
}

fun Any.logd(message: String) {
    val tag = "TESTLOG"
    val className = "[${this.javaClass.simpleName}]"
    val methodName = Thread.currentThread().stackTrace[3].methodName
    Log.d(tag, "$className $methodName: $message")
}
