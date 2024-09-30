package com.example.compusnow.screens.sing_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.SIGN_UP_SCREEN
import com.example.compusnow.common.ext.isValidEmail
import com.example.compusnow.common.ext.isValidPassword
import com.example.compusnow.common.ext.passwordMatches
import com.example.compusnow.common.snackbar.SnackbarManager
import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.LogService
import com.example.compusnow.screens.CompuSnowViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.example.compusnow.R.string as AppText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : CompuSnowViewModel(logService) {

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        viewModelScope.launch {
            try {
                // Se crea la cuenta
                accountService.linkAccount(email, password)

                // Después de crear la cuenta, cerramos la sesión inmediatamente
                accountService.signOut()
            } catch (e: Exception) {
                SnackbarManager.showMessage(AppText.sign_up_failed)
            }
        }
        launchCatching {
            accountService.linkAccount(email,password)
            openAndPopUp(LOGIN_SCREEN, SIGN_UP_SCREEN)
        }
    }

}
