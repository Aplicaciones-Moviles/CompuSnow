package com.example.compusnow.model.service.impl

import android.net.Uri
import com.example.compusnow.model.Product
import com.example.compusnow.model.service.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : StorageService {

    private val productCollection = firestore.collection("products")
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    override val products: Flow<List<Product>>
        get() = _products

    init {
        // Deshabilitar la persistencia del caché local
        firestore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = false
        }

        // Escuchar cambios en tiempo real desde Firestore
        listenToProductsInRealTime()
    }

    private fun listenToProductsInRealTime() {
        productCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al escuchar productos: ${error.message}")
                return@addSnapshotListener
            }

            snapshot?.let {
                val productsList = it.documents.mapNotNull { doc ->
                    val product = doc.toObject<Product>()
                    product?.let {
                        println("Producto recuperado en tiempo real: ${product.nombre}")
                    }
                    product
                }
                _products.value = productsList
            }
        }
    }

    override suspend fun getProduct(productId: String): Product? {
        return try {
            val snapshot = productCollection.document(productId).get().await()
            snapshot.toObject<Product>()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun save(product: Product, imageUri: Uri): String {
        return try {
            // Log para verificar que se está obteniendo la URL de la imagen
            println("Intentando subir la imagen...")

            val imageUrl = uploadImageToStorage(imageUri)
            if (imageUrl != null) {
                // Log para verificar que la URL es válida antes de guardar el producto
                println("URL de la imagen obtenida: $imageUrl")

                val productWithImage = product.copy(imagen = imageUrl)
                val newDoc = productCollection.add(productWithImage).await()

                println("Producto guardado con ID: ${newDoc.id}") // Log después de guardar
                return newDoc.id
            } else {
                println("Error: La URL de la imagen es nula") // Log si la URL es nula
                return ""
            }
        } catch (e: Exception) {
            println("Error al guardar el producto: ${e.message}") // Log para capturar excepciones
            return ""
        }
    }

    override suspend fun update(product: Product) {
        try {
            productCollection.document(product.id).set(product).await()
        } catch (e: Exception) {
            // Handle error
        }
    }

    override suspend fun delete(productId: String) {
        try {
            productCollection.document(productId).delete().await()
        } catch (e: Exception) {
            // Handle error
        }
    }

    private suspend fun uploadImageToStorage(imageUri: Uri): String? {
        return try {
            println("Iniciando la subida de la imagen...")

            // Referencia de almacenamiento
            val storageRef = FirebaseStorage.getInstance().reference.child("product_images/${UUID.randomUUID()}")

            // Subida del archivo
            val uploadTask = storageRef.putFile(imageUri).await()

            // Verifica si la tarea fue exitosa
            if (uploadTask.task.isSuccessful) {
                val downloadUrl = storageRef.downloadUrl.await().toString()
                println("Imagen subida con éxito. URL de descarga: $downloadUrl") // Log para verificar la URL
                return downloadUrl
            } else {
                println("Error: La subida de la imagen falló") // Log si la subida falla
                return null
            }
        } catch (e: Exception) {
            println("Error al subir la imagen: ${e.message}") // Log para capturar excepciones
            return null
        }
    }
}

