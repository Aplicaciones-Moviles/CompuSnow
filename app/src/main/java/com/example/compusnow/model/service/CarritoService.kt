package com.example.compusnow.model.service

import com.example.compusnow.model.Product
import com.example.compusnow.model.Carrito

interface CarritoService {
    suspend fun addToCart(userId: String, product: Product)  // Agrega un producto al carrito
    suspend fun loadCarritoFromFirestore(userId: String): Carrito  // Cargar el carrito desde Firestore
    suspend fun getCartItems(userId: String): List<Product>  // Obtener los productos del carrito
    suspend fun getTotalPrice(userId: String): Double  // Obtener el precio total del carrito
    suspend fun updateProductQuantity(userId: String, productId: String, newQuantity: Int)  // Actualizar cantidad de un producto
    suspend fun removeProductFromCart(userId: String, productId: String)  // Eliminar un producto del carrito
}
