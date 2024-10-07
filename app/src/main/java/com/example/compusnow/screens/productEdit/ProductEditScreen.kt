package com.example.compusnow.screens.productEdit

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.CompuSnowAppState
import com.example.compusnow.PRODUCT_EDIT_SCREEN
import com.example.compusnow.model.Product
import kotlinx.coroutines.launch

@Composable
fun ProductEditScreen(
    productId: String,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProductEditViewModel = hiltViewModel(),
    appState: CompuSnowAppState // Se pasa el appState para controlar el cambio de tema
) {
    // Observa el estado del producto desde el ViewModel
    val product by viewModel.product.collectAsState()

    // Variables locales para los campos editables
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    // Cargar el producto cuando se inicie la pantalla
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    // Actualizar los valores de los inputs cuando se cargue el producto
    LaunchedEffect(product) {
        product?.let {
            nombre = it.nombre
            descripcion = it.descripcion
            precio = it.precio.toString()
            stock = it.stock.toString()
        }
    }

    // Determina si el tema es oscuro para aplicar el degradado
    val backgroundModifier = if (appState.isDarkTheme) {
        Modifier.background(
            Brush.verticalGradient(
                listOf(
                    Color(0xFF14274E),
                    Color(0xFF2A60B0)
                )
            )
        )
    } else {
        Modifier.background(MaterialTheme.colors.background)
    }

    // Aplicar el fondo en la pantalla completa
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)  // Aplica el fondo dependiendo del tema
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección superior con botón de "Volver" y botón de tema
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón para volver al catálogo
            Button(onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_EDIT_SCREEN) }) {
                Text("Volver")
            }

            // Botón para alternar el tema
            IconButton(onClick = { appState.toggleTheme() }) {
                Icon(
                    imageVector = if (appState.isDarkTheme) Icons.Filled.Brightness4 else Icons.Filled.Brightness7,
                    contentDescription = "Cambiar Tema"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        product?.let {
            // Imagen del producto
            Image(
                painter = rememberImagePainter(data = it.imagen),
                contentDescription = it.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Inputs para editar el producto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para actualizar el producto
            Button(
                onClick = {
                    val updatedProduct = it.copy(
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0
                    )
                    viewModel.updateProduct(updatedProduct)
                    openAndPopUp(CATALOG_SCREEN, PRODUCT_EDIT_SCREEN)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary) // Usar el color primario del tema
            ) {
                Text("Actualizar Producto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para eliminar el producto
            Button(
                onClick = {
                    viewModel.deleteProduct(it.id)
                    openAndPopUp(CATALOG_SCREEN, PRODUCT_EDIT_SCREEN)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Text("Eliminar Producto")
            }
        } ?: if (product == null) {
            Text(text = "Producto no encontrado")
        } else {
            Text(text = "Cargando...")
        }
    }
}