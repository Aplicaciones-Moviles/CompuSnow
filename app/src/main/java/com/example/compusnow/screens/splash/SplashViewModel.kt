package com.example.compusnow.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.SPLASH_SCREEN
import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.LogService
import com.example.compusnow.screens.CompuSnowViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : CompuSnowViewModel(logService) {

    val showError = mutableStateOf(false)

    // Esta función se llamará cuando la app arranque
    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false

        // Verifica si el usuario ya está autenticado
        if (accountService.hasUser) {
            openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN) // Si hay usuario, navega a la pantalla de login
        } else {
            // Si no hay usuario, crea uno anónimo
            createAnonymousAccount(openAndPopUp)
        }
    }

    // Función para crear una cuenta anónima
    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount() // Intenta crear la cuenta anónima
            } catch (ex: FirebaseAuthException) {
                showError.value = true // En caso de error, muestra un mensaje
                throw ex // Lanza la excepción para que sea capturada por el flujo
            }
            // Si la creación es exitosa, navega a la pantalla de login
            openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
        }
    }
}
