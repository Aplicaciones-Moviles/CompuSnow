package com.example.compusnow.screens.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val carritoService: CarritoService = CarritoServiceImpl // Usar el Singleton directamente
) : ViewModel() {

    // Estado interno de los productos en el carrito
    private val _cartItems = MutableStateFlow(carritoService.getCartItems()) // Lista inicial de productos
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    // Estado interno del precio total
    private val _totalPrice = MutableStateFlow(carritoService.getTotalPrice()) // Precio total inicial
    val totalPrice: StateFlow<Double> = _totalPrice.asStateFlow()

    // Función para agregar productos al carrito
    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            carritoService.addToCart(product)
            println("Producto agregado: ${product.nombre}")
            _cartItems.value = carritoService.getCartItems()
            _totalPrice.value = carritoService.getTotalPrice()

            // Depuración para verificar la actualización de los productos
            println("Productos en el carrito: ${_cartItems.value}")
        }
    }
}
