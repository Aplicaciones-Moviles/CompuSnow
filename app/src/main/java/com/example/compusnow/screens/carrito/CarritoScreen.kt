package com.example.compusnow.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.CompuSnowAppState
import com.example.compusnow.PRODUCT_DETAIL_SCREEN

@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel = viewModel(),
    openAndPopUp: (String, String) -> Unit,
    userId: String, // Asegúrate de pasar el userId como parámetro
    appState: CompuSnowAppState // Para manejar el estado de la app (tema)
) {
    // Cargar el carrito cuando se renderiza la pantalla, observando el userId
    LaunchedEffect(userId) {
        println("Cargando carrito para el usuario: $userId")
        viewModel.loadCarrito(userId) // Llamar a la función para cargar el carrito desde Firestore
    }

    // Observar los flujos de datos del carrito y el precio total usando collectAsState
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)
            .padding(16.dp)
    ) {
        // Sección superior con botón de flecha, título centrado y botón de cambio de tema
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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

            // Texto centrado
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Carrito de Compras",
                    style = MaterialTheme.typography.h6,
                    color = Color.White // Cambiar color si es necesario
                )
            }

            // Botón para alternar el tema
            IconButton(onClick = { appState.toggleTheme() }) {
                Icon(
                    imageVector = if (appState.isDarkTheme) Icons.Filled.Brightness4 else Icons.Filled.Brightness7,
                    contentDescription = "Cambiar Tema",
                    tint = Color.White // Cambiar color si es necesario
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Indicador de carga en caso de que el carrito aún esté vacío
        if (cartItems.isEmpty()) {
            Text(
                text = "El carrito está vacío o se está cargando.",
                style = MaterialTheme.typography.body1
            )
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Imagen del producto
                            Image(
                                painter = rememberImagePainter(data = product.imagen),
                                contentDescription = product.nombre,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Información del producto (nombre, descripción, precio y cantidad)
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = product.nombre,
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = product.descripcion,
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Precio: $${product.precio}",
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Cantidad: ${product.cantidad}",
                                    style = MaterialTheme.typography.body2,
                                    color = Color.Gray
                                )

                                // Botones para actualizar cantidad y eliminar producto
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    // Botón para disminuir la cantidad
                                    Button(
                                        onClick = {
                                            val newQuantity = if (product.cantidad > 1) {
                                                product.cantidad - 1
                                            } else 1 // Evita que baje de 1
                                            viewModel.updateProductQuantity(userId, product.id, newQuantity)
                                        },
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Text("-")
                                    }

                                    // Botón para aumentar la cantidad
                                    Button(
                                        onClick = {
                                            val newQuantity = product.cantidad + 1
                                            viewModel.updateProductQuantity(userId, product.id, newQuantity)
                                        },
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Text("+")
                                    }

                                    // Botón para eliminar producto
                                    Button(
                                        onClick = {
                                            viewModel.removeProductFromCart(userId, product.id)
                                        },
                                        modifier = Modifier.padding(4.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                    ) {
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar el precio total
            Text(text = "Total: \$${totalPrice}", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de "Comprar"
            Button(
                onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_DETAIL_SCREEN) }, // Navegación usando openAndPopUp
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Comprar")
            }
        }
    }
}

