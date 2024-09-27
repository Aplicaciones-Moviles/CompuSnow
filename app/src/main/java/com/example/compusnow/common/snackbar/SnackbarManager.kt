package com.example.compusnow.common.snackbar


import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Maneja los mensajes Snackbar de forma centralizada
object SnackbarManager {
    // Almacena el mensaje actual en un flujo de estado mutable
    private val messages: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)

    // Expone el flujo de estado inmutable para la UI
    val snackbarMessages: StateFlow<SnackbarMessage?>
        get() = messages.asStateFlow()

    // Muestra un mensaje Snackbar usando un recurso de string
    fun showMessage(@StringRes message: Int) {
        messages.value = SnackbarMessage.ResourceSnackbar(message)
    }

    // Muestra un mensaje Snackbar usando una instancia de SnackbarMessage
    fun showMessage(message: SnackbarMessage) {
        messages.value = message
    }

    // Limpia el estado del Snackbar después de haber mostrado un mensaje
    fun clearSnackbarState() {
        messages.value = null
    }
}