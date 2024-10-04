package com.example.compusnow.model.service

import android.net.Uri
import com.example.compusnow.model.Product
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val products: Flow<List<Product>>
    suspend fun getProduct(productId: String): Product?
    suspend fun save(product: Product, imageUri: Uri): String
    suspend fun update(product: Product)
    suspend fun delete(productId: String)
}