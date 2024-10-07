package com.example.compusnow.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.PRODUCT_SCREEN
import com.example.compusnow.model.Product
import com.example.compusnow.model.service.AccountService
import com.example.compusnow.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log // Añade esta importación para usar logs
import com.example.compusnow.PRODUCT_EDIT_SCREEN

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : ViewModel() {

    // Estado que mantiene los productos almacenados en Firestore
    val products: StateFlow<List<Product>> = storageService.products
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Método para cerrar sesión
    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                accountService.signOut()
                openAndPopUp(LOGIN_SCREEN, CATALOG_SCREEN)
            } catch (e: Exception) {
                Log.e("CatalogViewModel", "Error al cerrar sesión: ${e.message}")
            }
        }
    }
    suspend fun getProductById(productId: String): Product? {
        return storageService.getProduct(productId)
    }
    // Método para navegar a la pantalla de edición de productos
    fun navigateToEditProduct(productId: String, openAndPopUp: (String, String) -> Unit) {
        openAndPopUp("$PRODUCT_EDIT_SCREEN/$productId", CATALOG_SCREEN)
    }
}
