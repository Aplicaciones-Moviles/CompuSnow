package com.example.compusnow.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compusnow.R
import com.example.compusnow.SPLASH_SCREEN
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.theme.CompuSnowTheme
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreenContent(
        onAppStart = { viewModel.onAppStart(openAndPopUp) },
        shouldShowError = viewModel.showError.value
    )
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit,
    shouldShowError: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco para resaltar el logo
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de splash
        Image(
            painter = painterResource(id = R.drawable.logo), // Asegúrate de que el logo esté en res/drawable
            contentDescription = "Logo de CompuSnow",
            modifier = Modifier.size(200.dp), // Ajusta el tamaño según necesites
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre logo y el cargador

        // Indicador de progreso mientras carga la app
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }

    // Lógica para esperar antes de cambiar de pantalla
    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        onAppStart() // Llamamos para cambiar a la siguiente pantalla
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    CompuSnowTheme {
        SplashScreenContent(
            onAppStart = { },
            shouldShowError = false
        )
    }
}
