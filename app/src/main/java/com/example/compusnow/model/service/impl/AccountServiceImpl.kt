package com.example.compusnow.model.service.impl


import android.util.Log
import com.example.compusnow.model.User
import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.trace
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d("AccountService", "Usuario autenticado con éxito")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AccountService", "Error: Credenciales inválidas", e)
            throw Exception("Correo o contraseña inválidos.")
        } catch (e: Exception) {
            Log.e("AccountService", "Error general al autenticar: ${e.message}", e)
            throw e
        }
    }

    override suspend fun sendRecoveryEmail(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
            Log.d("AccountService", "Correo de recuperación enviado con éxito")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Log.e("AccountService", "Error: Correo electrónico inválido", e)
            throw Exception("Correo electrónico inválido.")
        } catch (e: Exception) {
            Log.e("AccountService", "Error al enviar correo de recuperación: ${e.message}", e)
            throw e
        }
    }

    override suspend fun createAnonymousAccount() {
        try {
            auth.signInAnonymously().await()
            Log.d("AccountService", "Usuario anónimo creado con éxito")
        } catch (e: Exception) {
            Log.e("AccountService", "Error al crear cuenta anónima: ${e.message}", e)
            throw e
        }
    }

    override suspend fun linkAccount(email: String, password: String) {
        trace(LINK_ACCOUNT_TRACE) {
            try {
                val credential = EmailAuthProvider.getCredential(email, password)
                auth.currentUser?.let {
                    it.linkWithCredential(credential).await()
                    Log.d("AccountService", "Cuenta vinculada con éxito: $email")
                } ?: throw Exception("No hay un usuario autenticado para vincular.")
            } catch (e: FirebaseAuthWeakPasswordException) {
                Log.e("AccountService", "La contraseña es demasiado débil.", e)
                throw Exception("La contraseña es demasiado débil.")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.e("AccountService", "El correo electrónico es inválido.", e)
                throw Exception("El correo electrónico es inválido.")
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.e("AccountService", "El correo electrónico ya está en uso.", e)
                throw Exception("El correo electrónico ya está en uso.")
            } catch (e: Exception) {
                Log.e("AccountService", "Error al vincular la cuenta: ${e.message}", e)
                throw e
            }
        }
    }

    override suspend fun deleteAccount() {
        try {
            auth.currentUser?.delete()?.await() ?: throw Exception("No hay un usuario autenticado para eliminar.")
            Log.d("AccountService", "Cuenta eliminada con éxito")
        } catch (e: Exception) {
            Log.e("AccountService", "Error al eliminar la cuenta: ${e.message}", e)
            throw e
        }
    }

    override suspend fun signOut() {
        try {
            auth.currentUser?.let { currentUser ->
                if (currentUser.isAnonymous) {
                    currentUser.delete().await()
                    Log.d("AccountService", "Usuario anónimo eliminado")
                }
            } ?: throw Exception("No hay un usuario autenticado.")

            auth.signOut()
            Log.d("AccountService", "Usuario desconectado")

            // Crear una nueva cuenta anónima
            createAnonymousAccount()
        } catch (e: Exception) {
            Log.e("AccountService", "Error al cerrar sesión: ${e.message}", e)
            throw e
        }
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}

