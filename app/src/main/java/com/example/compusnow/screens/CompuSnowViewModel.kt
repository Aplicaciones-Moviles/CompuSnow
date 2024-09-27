package com.example.compusnow.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.common.snackbar.SnackbarManager
import com.example.compusnow.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.compusnow.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class CompuSnowViewModel(private val logService: LogService) : ViewModel() {
    // Función que lanza una corrutina manejando errores
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}