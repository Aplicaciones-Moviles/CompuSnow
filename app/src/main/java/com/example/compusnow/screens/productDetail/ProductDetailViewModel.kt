package com.example.compusnow.screens.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compusnow.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            // Aquí puedes cargar el producto desde Firestore u otra fuente de datos
            // Luego lo asignas al estado de _product
        }
    }
}