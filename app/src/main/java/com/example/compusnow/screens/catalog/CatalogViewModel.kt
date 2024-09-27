package com.example.compusnow.screens.catalog

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.LOGIN_SCREEN
import com.example.compusnow.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val accountService: AccountService // Inyectamos el servicio de cuentas
) : ViewModel() {

    // Lista mutable para los ítems del catálogo
    val catalogItems = mutableStateListOf<String>()

    // Inicialización: cargar los ítems cuando se inicializa el ViewModel
    init {
        loadCatalogItems()
    }

    // Función para cargar los ítems del catálogo (puede reemplazarse con datos de API o base de datos)
    private fun loadCatalogItems() {
        // Cargar datos de prueba (puede reemplazarse con datos reales)
        catalogItems.addAll(
            listOf(
                "Ventana de aluminio",
                "Puerta de vidrio",
                "Closet de melamina",
                "Escritorio de oficina"
            )
        )
    }

    // Método para agregar nuevos ítems al catálogo
    fun addItem(item: String) {
        if (item.isNotBlank()) {
            catalogItems.add(item)
        }
    }

    // Método para eliminar un ítem del catálogo
    fun removeItem(item: String) {
        catalogItems.remove(item)
    }

    // Método para cerrar sesión
    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            accountService.signOut() // Llamada suspendida dentro de una corutina
            openAndPopUp(LOGIN_SCREEN, "CatalogScreen") // Navega al login después de cerrar sesión
        }
    }

}
