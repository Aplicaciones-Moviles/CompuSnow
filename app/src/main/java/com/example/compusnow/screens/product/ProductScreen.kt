package com.example.compusnow.screens.product

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.CompuSnowAppState
import com.example.compusnow.PRODUCT_SCREEN
import com.example.compusnow.R
import com.example.compusnow.model.Product
import java.io.InputStream

@Composable
fun ProductScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavController,
    appState: CompuSnowAppState
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current

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

    BackHandler {
        if (!navController.popBackStack()) {
            navController.navigate(CATALOG_SCREEN)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF14274E), Color(0xFF2A60B0))))
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Button(
                    onClick = { navController.navigate(CATALOG_SCREEN) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .defaultMinSize(minHeight = 48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
                ) {
                    Text(text = "Volver al Catálogo", color = Color.White)
                }

                // Botón para alternar el tema
                IconButton(onClick = { appState.toggleTheme() }) {
                    Icon(
                        imageVector = if (appState.isDarkTheme) Icons.Filled.Brightness4 else Icons.Filled.Brightness7,
                        contentDescription = "Cambiar Tema",
                        tint = Color.White
                    )
                }
            }

            item {
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
            }

            item {
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
            }

            item {
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
            }

            item {
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
            }

            item {
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
            }

            item {
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Imagen del Producto",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(8.dp)
                    )
                }

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
                ) {
                    Text("Seleccionar Imagen", color = Color.White)
                }
            }

            item {
                if (showError) {
                    Text(
                        text = stringResource(id = R.string.empty_fields_error),
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Button(
                    onClick = {
                        if (nombre.isNotBlank() && precio.isNotBlank() && stock.isNotBlank()) {
                            val precioDouble = precio.toDoubleOrNull() ?: 0.0
                            val stockInt = stock.toIntOrNull() ?: 0
                            val newProduct = Product(
                                id = "", // ID vacío ya que no es actualización
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precioDouble,
                                categoria = categoria,
                                stock = stockInt
                            )

                            imageUri?.let {
                                viewModel.addProduct(newProduct, it)
                                openAndPopUp(CATALOG_SCREEN, PRODUCT_SCREEN)
                            }
                        } else {
                            showError = true
                            Log.e("ProductScreen", "Por favor completa todos los campos")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
                ) {
                    Text("Guardar Producto", color = Color.White)
                }
            }
        }
    }
}
