package com.example.compusnow.screens.productEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.model.Product
import com.example.compusnow.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductEditViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    // Cargar un producto específico por su ID
    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val product = storageService.getProduct(productId)
            _product.value = product
        }
    }


    // Actualizar el producto
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                storageService.update(product)
                println("Producto actualizado con éxito")
            } catch (e: Exception) {
                println("Error al actualizar el producto: ${e.message}")
            }
        }
    }

    // Eliminar el producto
    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                storageService.delete(productId)
                println("Producto eliminado con éxito")
            } catch (e: Exception) {
                println("Error al eliminar el producto: ${e.message}")
            }
        }
    }
}
