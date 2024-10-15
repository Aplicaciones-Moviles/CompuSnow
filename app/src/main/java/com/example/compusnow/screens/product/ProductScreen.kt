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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.compusnow.PRODUCT_DETAIL_SCREEN
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

    // Define los colores de los inputs según el tema
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = if (appState.isDarkTheme) Color.White else Color(0xFF2A60B0),
        placeholderColor = if (appState.isDarkTheme) Color.Black else Color(0xFF2A60B0),
        focusedBorderColor = if (appState.isDarkTheme) Color.Black else Color(0xFF2A60B0),
        unfocusedBorderColor = if (appState.isDarkTheme) Color.Black else Color(0xFF2A60B0),
        focusedLabelColor = if (appState.isDarkTheme) Color.White else Color(0xFF2A60B0),
        unfocusedLabelColor = if (appState.isDarkTheme) Color.White else Color(0xFF2A60B0)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Sección superior con botón de flecha y botón de tema
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón con flecha para regresar al catálogo
                    IconButton(
                        onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_DETAIL_SCREEN) },
                        modifier = Modifier
                            .size(48.dp) // Tamaño del botón
                            .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF00A9F4), Color(0xFF3F86F4)) // Degradado de colores
                                )
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver al Catálogo",
                            tint = Color.White // Flecha en color blanco
                        )
                    }

                    // Botón para alternar el tema
                    IconButton(onClick = { appState.toggleTheme() }) {
                        Icon(
                            imageVector = if (appState.isDarkTheme) Icons.Filled.Brightness4 else Icons.Filled.Brightness7,
                            contentDescription = "Cambiar Tema"
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors // Aplicar los colores personalizados
                )
            }

            item {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors // Aplicar los colores personalizados
                )
            }

            item {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = textFieldColors // Aplicar los colores personalizados
                )
            }

            item {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors // Aplicar los colores personalizados
                )
            }

            item {
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = textFieldColors // Aplicar los colores personalizados
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text("Seleccionar Imagen", color = MaterialTheme.colors.onPrimary)
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text("Guardar Producto", color = MaterialTheme.colors.onPrimary)
                }
            }
        }
    }
}