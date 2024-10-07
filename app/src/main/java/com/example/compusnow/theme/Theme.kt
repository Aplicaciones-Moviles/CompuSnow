package com.example.compusnow.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background

// Definir los colores para el modo oscuro
private val DarkColorPalette = darkColors(
    primary = Color(0xFF0F244D),  // Azul oscuro para el botón
    background = Color(0xFF2A60B0),  // Fondo azul oscuro
    surface = Color(0xFF1B3F7D),   // Azul más claro para cuadros, tarjetas y diálogos
    onPrimary = Color.White,      // Texto blanco sobre el botón azul oscuro
    error = Color(0xFFD32F2F),    // Rojo claro para errores
    onError = Color.Black,        // Texto negro sobre botones de error
    onBackground = Color.White,   // Texto blanco en fondo
    onSurface = Color.White       // Texto blanco sobre cuadros, tarjetas y diálogos
)

// Definir los colores para el modo claro
private val LightColorPalette = lightColors(
    primary = Color(0xFF173868),  // Azul claro para el botón
    background = Color.White,     // Fondo blanco
    surface = Color(0x5B023A97),  // Azul oscuro para cuadros, tarjetas y diálogos
    onPrimary = Color.White,      // Texto blanco sobre el botón azul claro
    error = Color(0xFFD32F2F),    // Rojo para errores
    onError = Color.White,        // Texto blanco sobre botones de error
    onBackground = Color.Black,   // Texto negro en fondo blanco
    onSurface = Color.White       // Texto blanco sobre cuadros, tarjetas y diálogos
)

@Composable
fun CompuSnowTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        content()
    }
}

