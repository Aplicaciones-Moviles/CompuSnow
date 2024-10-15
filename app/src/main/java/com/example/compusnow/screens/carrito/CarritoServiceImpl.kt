package com.example.compusnow.screens.carrito

import com.example.compusnow.model.Carrito
import com.example.compusnow.model.Product

// Definimos CarritoServiceImpl como un Singleton
object CarritoServiceImpl : CarritoService {
    private val carrito = Carrito()

    // Función para agregar un producto al carrito
    override fun addToCart(product: Product) {
        carrito.products.add(product) // Añadir el producto a la lista
        carrito.totalPrice += product.precio // Actualizar el precio total
        println("Producto añadido a la lista: ${carrito.products}")
    }

    // Función para obtener los productos del carrito
    override fun getCartItems(): List<Product> {
        return carrito.products // Retornar la lista de productos
    }

    // Función para obtener el precio total
    override fun getTotalPrice(): Double {
        return carrito.totalPrice // Retornar el precio total
    }
}
