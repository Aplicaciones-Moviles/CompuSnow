package com.example.compusnow.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definir los colores para el modo oscuro
private val DarkColorPalette = darkColors(
    primary = Color(0xFF0F244D),  // primary_dark_blue
    secondary = Color(0xFF2A60B0),  // secondary_light_blue
    background = Color(0xFF0F244D),  // Fondo similar al primario
    surface = Color(0xFF1B3F7D),  // button_dark_blue
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

// Definir los colores para el modo claro
private val LightColorPalette = lightColors(
    primary = Color(0xFF2A60B0),  // secondary_light_blue
    secondary = Color(0xFFFFA726),  // accent_orange
    background = Color.White,
    surface = Color(0xFF1B3F7D),  // button_dark_blue
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF2E2E2E),  // text_gray
    onSurface = Color.White
)

@Composable
fun CompuSnowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,  // Asegúrate de tener este archivo Typography correctamente configurado
        shapes = Shapes,  // Asegúrate de tener las formas configuradas en un archivo Shapes.kt
        content = content
    )
}
