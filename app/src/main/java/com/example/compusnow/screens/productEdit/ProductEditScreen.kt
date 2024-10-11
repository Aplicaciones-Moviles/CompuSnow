package com.example.compusnow.screens.productEdit

<<<<<<< HEAD
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
=======
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
=======
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.CompuSnowAppState
<<<<<<< HEAD
import com.example.compusnow.PRODUCT_DETAIL_SCREEN
import com.example.compusnow.PRODUCT_EDIT_SCREEN
import com.example.compusnow.model.Product
=======
import com.example.compusnow.PRODUCT_EDIT_SCREEN
import com.example.compusnow.model.Product
import kotlinx.coroutines.launch
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2

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

<<<<<<< HEAD
    // Define los colores de los inputs según el tema
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = if (appState.isDarkTheme) Color.White else Color(0xFF2A60B0), // Azul en claro, blanco en oscuro
        placeholderColor = if (appState.isDarkTheme) Color.Black else Color(0xFF2A60B0), // Placeholder: negro en oscuro, azul en claro
        focusedBorderColor = if (appState.isDarkTheme) Color.Black else Color(0xFF2A60B0), // Azul en claro, negro en oscuro
        unfocusedBorderColor = if (appState.isDarkTheme) Color.Black else Color(0xFF2A60B0), // Azul en claro, negro en oscuro
        focusedLabelColor = if (appState.isDarkTheme) Color.White else Color(0xFF2A60B0), // Color del label enfocado
        unfocusedLabelColor = if (appState.isDarkTheme) Color.White else Color(0xFF2A60B0) // Color del label no enfocado
    )

    // Función para mostrar la imagen del producto con el cuadro redondeado
    @Composable
    fun ProductImage(imageUrl: String) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
                .background(MaterialTheme.colors.surface)
        ) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null, // Descripción para accesibilidad
                modifier = Modifier
                    .fillMaxSize() // La imagen llena todo el cuadro
                    .clip(RoundedCornerShape(16.dp)) // Bordes redondeados también para la imagen
            )
        }
    }

    // Aplicar el fondo en la pantalla completa con scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Habilitar el scroll vertical
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
=======
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
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
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
<<<<<<< HEAD
            // Imagen del producto con cuadro redondeado
            ProductImage(imageUrl = it.imagen)
=======
            // Imagen del producto
            Image(
                painter = rememberImagePainter(data = it.imagen),
                contentDescription = it.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2

            Spacer(modifier = Modifier.height(16.dp))

            // Inputs para editar el producto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Producto") },
<<<<<<< HEAD
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors // Aplicar los colores personalizados
=======
                modifier = Modifier.fillMaxWidth()
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
<<<<<<< HEAD
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors // Aplicar los colores personalizados
=======
                modifier = Modifier.fillMaxWidth()
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
<<<<<<< HEAD
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = textFieldColors // Aplicar los colores personalizados
=======
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth(),
<<<<<<< HEAD
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = textFieldColors // Aplicar los colores personalizados
=======
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
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
<<<<<<< HEAD
}
=======
}
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
