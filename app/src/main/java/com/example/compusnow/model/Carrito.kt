package com.example.compusnow.model

data class Carrito(
    val products: MutableList<Product> = mutableListOf(),
    var totalPrice: Double = 0.0,
    val userId: String = ""  // ID del usuario que posee el carrito
)
