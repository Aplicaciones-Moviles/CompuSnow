package com.example.compusnow.model

import com.google.firebase.firestore.DocumentId

data class Product(
    @DocumentId val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val imagen: String = "",
    val categoria: String = "",
    val stock: Int = 0,
    val cantidad: Int = 1,
    val userId: String = ""
)