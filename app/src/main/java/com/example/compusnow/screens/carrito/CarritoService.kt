package com.example.compusnow.screens.carrito

import com.example.compusnow.model.Product

interface CarritoService {
    fun addToCart(product: Product)
    fun getCartItems(): List<Product>
    fun getTotalPrice(): Double
}
