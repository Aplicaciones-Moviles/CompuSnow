package com.example.compusnow.model.service.impl

import com.example.compusnow.model.Carrito
import com.example.compusnow.model.Product
import com.example.compusnow.model.service.CarritoService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarritoServiceImpl : CarritoService {

    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun addToCart(userId: String, product: Product) {
        val carritoRef = firestore.collection("carritos").document(userId)
        val carritoSnapshot = carritoRef.get().await()

        println("Añadiendo producto al carrito para usuario: $userId")
        println("Datos del producto: ${product.nombre}, ID: ${product.id}, Precio: ${product.precio}, Cantidad inicial: ${product.cantidad}")

        if (carritoSnapshot.exists()) {
            // Si el carrito ya existe, actualízalo
            val carrito = carritoSnapshot.toObject(Carrito::class.java)
            carrito?.let {
                val updatedProducts = it.products.toMutableList()

                // Verifica si el producto ya existe en el carrito
                val existingProduct = updatedProducts.find { prod -> prod.id == product.id }

                if (existingProduct != null) {
                    // Si ya existe, incrementa la cantidad
                    val updatedProduct = existingProduct.copy(cantidad = existingProduct.cantidad + 1)
                    updatedProducts[updatedProducts.indexOf(existingProduct)] = updatedProduct

                    println("Producto existente encontrado, actualizando cantidad: ${updatedProduct.cantidad}")
                } else {
                    // Si no existe, agrega un nuevo producto
                    val newProduct = product.copy(cantidad = 1)
                    updatedProducts.add(newProduct)

                    println("Producto nuevo agregado al carrito: ${newProduct.nombre}, ID: ${newProduct.id}, Cantidad: ${newProduct.cantidad}")
                }

                val updatedTotalPrice = updatedProducts.sumOf { it.precio * it.cantidad }
                println("Precio total actualizado: $updatedTotalPrice")

                // Guarda el carrito actualizado
                carritoRef.set(Carrito(it.id, updatedProducts, updatedTotalPrice, userId)).await()
                println("Carrito actualizado en Firestore para usuario: $userId")
            }
        } else {
            // Si no existe el carrito, créalo
            val newProduct = product.copy(cantidad = 1)

            val newCarrito = Carrito(
                products = listOf(newProduct),
                totalPrice = product.precio,
                userId = userId
            )
            carritoRef.set(newCarrito).await()

            println("Nuevo carrito creado en Firestore para usuario: $userId")
            println("Producto agregado al nuevo carrito: ${newProduct.nombre}, ID: ${newProduct.id}, Cantidad: ${newProduct.cantidad}")
        }
    }

    // Cargar el carrito desde Firestore
    override suspend fun loadCarritoFromFirestore(userId: String): Carrito {
        val carritoRef = firestore.collection("carritos").document(userId)
        val carritoSnapshot = carritoRef.get().await()

        return if (carritoSnapshot.exists()) {
            carritoSnapshot.toObject(Carrito::class.java) ?: Carrito(userId = userId)
        } else {
            // Si no existe el carrito, devolver uno vacío
            Carrito(userId = userId)
        }
    }

    // Obtener los productos del carrito
    override suspend fun getCartItems(userId: String): List<Product> {
        val carrito = loadCarritoFromFirestore(userId)
        return carrito.products
    }

    // Obtener el precio total del carrito
    override suspend fun getTotalPrice(userId: String): Double {
        val carrito = loadCarritoFromFirestore(userId)
        return carrito.totalPrice
    }

    // Actualizar la cantidad de un producto en el carrito
    override suspend fun updateProductQuantity(userId: String, productId: String, newQuantity: Int) {
        val carritoRef = firestore.collection("carritos").document(userId)
        val carritoSnapshot = carritoRef.get().await()

        if (carritoSnapshot.exists()) {
            val carrito = carritoSnapshot.toObject(Carrito::class.java)
            carrito?.let {
                val updatedProducts = it.products.map { product ->
                    if (product.id == productId) {
                        product.copy(cantidad = newQuantity.coerceAtLeast(1)) // Asegurarse de que la cantidad no sea menor a 1
                    } else product
                }

                val updatedTotalPrice = updatedProducts.sumOf { it.precio * it.cantidad }

                // Guarda el carrito actualizado
                carritoRef.set(Carrito(it.id, updatedProducts, updatedTotalPrice, userId)).await()
            }
        }
    }

    // Eliminar un producto del carrito
    override suspend fun removeProductFromCart(userId: String, productId: String) {
        val carritoRef = firestore.collection("carritos").document(userId)
        val carritoSnapshot = carritoRef.get().await()

        if (carritoSnapshot.exists()) {
            val carrito = carritoSnapshot.toObject(Carrito::class.java)
            carrito?.let {
                val updatedProducts = it.products.filterNot { product -> product.id == productId }

                val updatedTotalPrice = updatedProducts.sumOf { product -> product.precio * product.cantidad }

                // Actualizar el carrito con los productos restantes y el precio correcto
                carritoRef.set(Carrito(it.id, updatedProducts, updatedTotalPrice, userId)).await()

                println("Producto eliminado del carrito con ID: $productId")
            }
        }
    }
}