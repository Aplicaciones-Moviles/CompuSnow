package com.example.compusnow.model

import com.google.firebase.firestore.DocumentId

data class Carrito(
    @DocumentId val id: String = "",  // ID del documento de Firestore
    val products: List<Product> = listOf(),  // Lista de productos, cada uno con su propio ID
    var totalPrice: Double = 0.0,  // Precio total del carrito
    val userId: String = ""  // ID del usuario que posee el carrito
)