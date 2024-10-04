package com.example.compusnow.screens.product

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.model.Product
import com.example.compusnow.model.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log // Añade esta importación para usar logs
import kotlinx.coroutines.GlobalScope

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val storageService: StorageService
) : ViewModel() {

    var currentProduct = mutableStateOf<Product?>(null)

    // Función para agregar un producto con imagen
    fun addProduct(product: Product, imageUri: Uri) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val productWithUserId = product.copy(userId = userId)

        Log.d("ProductViewModel", "Agregando producto: ${product.nombre}, userId: $userId")

        GlobalScope.launch {
            try {
                if (imageUri.toString().isNotEmpty()) {
                    Log.d("ProductViewModel", "Subiendo imagen desde URI: $imageUri")

                    val productId = storageService.save(productWithUserId, imageUri)

                    if (productId.isNotEmpty()) {
                        Log.d("ProductViewModel", "Producto guardado con ID: $productId")
                    } else {
                        Log.e("ProductViewModel", "Error: No se pudo guardar el producto")
                    }
                } else {
                    Log.e("ProductViewModel", "Error: La URI de la imagen es inválida")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al agregar producto: ${e.message}")
            }
        }
    }

    // Función para cargar los detalles del producto actual para actualizar
    fun loadProduct(productId: String) {
        Log.d("ProductViewModel", "Cargando producto con ID: $productId")

        viewModelScope.launch {
            try {
                val product = storageService.getProduct(productId)
                currentProduct.value = product
                product?.let {
                    Log.d("ProductViewModel", "Producto cargado: ${it.nombre}, Precio: ${it.precio}")
                } ?: Log.e("ProductViewModel", "Error: Producto no encontrado con ID: $productId")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al cargar el producto: ${e.message}")
            }
        }
    }

    // Función para actualizar un producto existente
    fun updateProduct(product: Product) {
        Log.d("ProductViewModel", "Actualizando producto: ${product.nombre}, ID: ${product.id}")

        viewModelScope.launch {
            try {
                storageService.update(product)
                Log.d("ProductViewModel", "Producto actualizado correctamente")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al actualizar producto: ${e.message}")
            }
        }
    }
}