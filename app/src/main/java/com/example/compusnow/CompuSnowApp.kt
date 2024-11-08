package com.example.compusnow

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compusnow.common.snackbar.SnackbarManager
import com.example.compusnow.screens.carrito.CarritoScreen
import com.example.compusnow.screens.catalog.CatalogScreen
import com.example.compusnow.screens.login.LoginScreen
import com.example.compusnow.screens.product.ProductScreen
import com.example.compusnow.screens.productDetail.ProductDetailScreen
import com.example.compusnow.screens.productEdit.ProductEditScreen
import com.example.compusnow.screens.sing_up.SignUpScreen
import com.example.compusnow.screens.splash.SplashScreen
import com.example.compusnow.theme.CompuSnowTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterialApi
fun CompuSnowApp() {
    val firestore = FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(false) // Deshabilitar persistencia
        .build()

    firestore.firestoreSettings = settings

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    // Obtener el estado de la aplicación (incluyendo el tema actual)
    val appState = rememberAppState(userId = userId)

    // Cambiar el tema basado en `isDarkTheme`
    CompuSnowTheme(isDarkTheme = appState.isDarkTheme) {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    compuSnowGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    userId: String
): CompuSnowAppState =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        CompuSnowAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope, userId)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.compuSnowGraph(appState: CompuSnowAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(LOGIN_SCREEN) {
        LoginScreen(appState = appState, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(CATALOG_SCREEN) {
        CatalogScreen(appState = appState, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(appState = appState, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(PRODUCT_SCREEN) {
        ProductScreen(
            appState = appState,
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            navController = appState.navController
        )
    }
    composable("$PRODUCT_DETAIL_SCREEN/{productId}") { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId") ?: ""
        ProductDetailScreen(
            productId = productId,
            appState = appState,
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            navController = appState.navController
        )
    }
    composable("$PRODUCT_EDIT_SCREEN/{productId}") { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId") ?: ""
        ProductEditScreen(
            productId = productId,
            appState = appState,
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
    composable(route = "carrito") {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        CarritoScreen(
            viewModel = viewModel(),
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            userId = userId, // Pasamos el userId al CarritoScreen
            appState = appState,
        )
    }

}
