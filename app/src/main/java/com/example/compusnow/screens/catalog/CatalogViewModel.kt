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

    // Método para eliminar un producto
    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                Log.d("CatalogViewModel", "Eliminando producto con ID: $productId")
                storageService.delete(productId)
            } catch (e: Exception) {
                Log.e("CatalogViewModel", "Error al eliminar producto: ${e.message}")
            }
        }
    }

    // Método para actualizar un producto
    fun updateProduct(openAndPopUp: (String, String) -> Unit, productId: String) {
        // Añade un log para verificar si se está pasando el productId correctamente
        Log.d("CatalogViewModel", "Navegando a la pantalla de producto para actualizar el ID: $productId")

        // Navegar a la pantalla de producto con el productId
        openAndPopUp(PRODUCT_SCREEN, productId)
    }
    suspend fun getProductById(productId: String): Product? {
        return storageService.getProduct(productId)
    }
}
