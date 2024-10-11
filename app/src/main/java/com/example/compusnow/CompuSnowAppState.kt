package com.example.compusnow

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.compusnow.common.snackbar.SnackbarManager
import com.example.compusnow.common.snackbar.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class CompuSnowAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    // Mutable state to track the current theme (dark or light)
    var isDarkTheme by mutableStateOf(false)
        private set

    // Initialize coroutine to handle snackbar messages
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
                snackbarManager.clearSnackbarState()
            }
        }
    }

    // Function to toggle between dark and light themes
    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }

    // Function to pop up from the back stack
    fun popUp() {
        navController.popBackStack()
    }

    // Function to navigate to a route
    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    // Function to navigate and pop up
    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    // Function to clear the back stack and navigate to a new route
    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}