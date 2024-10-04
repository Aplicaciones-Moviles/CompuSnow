package com.example.compusnow.screens.product

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.PRODUCT_SCREEN
import com.example.compusnow.model.Product
import java.io.InputStream

@Composable
fun ProductScreen(
    openAndPopUp: (String, String) -> Unit,
    productId: String? = null, // Se puede pasar un ID para actualización
    viewModel: ProductViewModel = hiltViewModel() // Inyecta el ViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val context = LocalContext.current

    // Cargar el producto si productId no es nulo (si estamos actualizando)
    val currentProduct = viewModel.currentProduct.value
    LaunchedEffect(productId) {
        productId?.let {
            viewModel.loadProduct(it)
        }
    }

    // Prellenar los campos si estamos actualizando un producto
    LaunchedEffect(currentProduct) {
        currentProduct?.let {
            nombre = it.nombre
            descripcion = it.descripcion
            precio = it.precio.toString()
            categoria = it.categoria
            stock = it.stock.toString()
        }
    }

    // ActivityResultLauncher para seleccionar una imagen desde la galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                inputStream?.let { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    imageBitmap = bitmap.asImageBitmap()
                }
            } catch (e: Exception) {
                Log.e("ProductScreen", "Error al cargar imagen: ${e.message}")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF14274E), Color(0xFF2A60B0))))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // Input para el nombre del producto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Producto", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF14274E)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFF1B3F7D),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )

            // Input para la descripción del producto
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF14274E)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFF1B3F7D),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )

            // Input para el precio
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF14274E)),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFF1B3F7D),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )

            // Input para la categoría
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF14274E)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFF1B3F7D),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )

            // Input para el stock
            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF14274E)),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color(0xFF1B3F7D),
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar imagen seleccionada (si existe)
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Imagen del Producto",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
            }

            // Botón para seleccionar imagen
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
            ) {
                Text("Seleccionar Imagen", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar el producto (crear o actualizar)
            Button(
                onClick = {
                    if (nombre.isNotBlank() && precio.isNotBlank() && stock.isNotBlank()) {
                        val precioDouble = precio.toDoubleOrNull() ?: 0.0
                        val stockInt = stock.toIntOrNull() ?: 0
                        val newProduct = Product(
                            id = currentProduct?.id ?: "", // Si es actualización, toma el ID existente
                            nombre = nombre,
                            descripcion = descripcion,
                            precio = precioDouble,
                            categoria = categoria,
                            stock = stockInt
                        )

                        imageUri?.let {
                            if (productId == null) {
                                // Crear nuevo producto
                                viewModel.addProduct(newProduct, it)
                            } else {
                                // Actualizar producto existente
                                viewModel.updateProduct(newProduct)
                            }
                            openAndPopUp(CATALOG_SCREEN, PRODUCT_SCREEN)
                        }
                    } else {
                        Log.e("ProductScreen", "Por favor completa todos los campos")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
            ) {
                Text(if (productId == null) "Guardar Producto" else "Actualizar Producto", color = Color.White)
            }
        }
    }
}