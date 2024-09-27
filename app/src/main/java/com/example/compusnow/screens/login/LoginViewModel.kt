package com.example.compusnow.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.SIGN_UP_SCREEN
import com.example.compusnow.R.string as AppText
import com.example.compusnow.common.ext.isValidEmail
import com.example.compusnow.common.snackbar.SnackbarManager
import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.LogService
import com.example.compusnow.screens.CompuSnowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : CompuSnowViewModel(logService) {

    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email: String
        get() = uiState.value.email

    private val password: String
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(CATALOG_SCREEN, LOGIN_SCREEN) // Cambia a la pantalla de catálogo
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }
}
