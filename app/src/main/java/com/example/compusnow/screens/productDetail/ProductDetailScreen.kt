package com.example.compusnow.screens.productDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.PRODUCT_DETAIL_SCREEN
import com.example.compusnow.model.Product
import com.example.compusnow.screens.catalog.CatalogViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: String,
    openAndPopUp: (String, String) -> Unit
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    var product by remember { mutableStateOf<Product?>(null) }
    var productNotFound by remember { mutableStateOf(false) }

    // Cargar el producto asíncronamente
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        coroutineScope.launch {
            val result = viewModel.getProductById(productId)
            if (result != null) {
                product = result
            } else {
                productNotFound = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_DETAIL_SCREEN) }) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        product?.let {
            Image(
                painter = rememberImagePainter(data = it.imagen),
                contentDescription = it.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = it.nombre)
            Text(text = it.descripcion)
            Text(text = "Precio: \$${it.precio}")
            Text(text = "Stock: ${it.stock}")
        } ?: if (productNotFound) {
            Text(text = "Producto no encontrado")
        } else {
            Text(text = "Cargando...")
        }
    }
}