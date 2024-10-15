package com.example.compusnow.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.model.Product
import com.example.compusnow.model.service.CarritoService
import com.example.compusnow.model.service.impl.CarritoServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val carritoService: CarritoService = CarritoServiceImpl() // Usar el Singleton directamente
) : ViewModel() {

    // Estado interno de los productos en el carrito
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    // Estado interno del precio total
    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    // Cargar el carrito desde Firestore
    fun loadCarrito(userId: String) {
        viewModelScope.launch {
            try {
                val carrito = carritoService.loadCarritoFromFirestore(userId)
                _cartItems.value = carrito.products // Actualizar la lista de productos
                _totalPrice.value = carrito.totalPrice // Actualizar el precio total
                println("Productos en el carrito: ${carrito.products}")
                println("Precio total actualizado: ${carrito.totalPrice}")
            } catch (e: Exception) {
                println("Error al cargar el carrito: ${e.message}")
            }
        }
    }

    fun addProductToCart(userId: String, product: Product) {
        viewModelScope.launch {
            try {
                carritoService.addToCart(userId, product)
                loadCarrito(userId)  // Cargar el carrito actualizado
            } catch (e: Exception) {
                println("Error al agregar producto al carrito: ${e.message}")
            }
        }
    }

    fun updateProductQuantity(userId: String, productId: String, newQuantity: Int) {
        viewModelScope.launch {
            try {
                carritoService.updateProductQuantity(userId, productId, newQuantity)
                loadCarrito(userId)  // Cargar el carrito actualizado
            } catch (e: Exception) {
                println("Error al actualizar la cantidad del producto: ${e.message}")
            }
        }
    }

    fun removeProductFromCart(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                carritoService.removeProductFromCart(userId, productId)
                loadCarrito(userId)  // Cargar el carrito actualizado
            } catch (e: Exception) {
                println("Error al eliminar producto del carrito: ${e.message}")
            }
        }
}
}
