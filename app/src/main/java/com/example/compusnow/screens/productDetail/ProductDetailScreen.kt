package com.example.compusnow.screens.productDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
<<<<<<< HEAD
import androidx.compose.material.icons.filled.ArrowBack
=======
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.CompuSnowAppState
import com.example.compusnow.PRODUCT_DETAIL_SCREEN
import com.example.compusnow.model.Product
import com.example.compusnow.screens.carrito.CarritoViewModel
import com.example.compusnow.screens.catalog.CatalogViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: String,
    openAndPopUp: (String, String) -> Unit,
    navController: NavController,
<<<<<<< HEAD
    appState: CompuSnowAppState,
    carritoViewModel: CarritoViewModel = hiltViewModel() // ViewModel para manejar el carrito
=======
    appState: CompuSnowAppState // Asegúrate de pasar el appState para el control del tema
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    var product by remember { mutableStateOf<Product?>(null) }
    var productNotFound by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Obtenemos el producto basado en su ID
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

<<<<<<< HEAD
    // Maneja el evento de volver atrás
=======
    // Manejar el botón de retroceso
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
    BackHandler {
        if (!navController.popBackStack()) {
            navController.navigate(CATALOG_SCREEN)
        }
    }

<<<<<<< HEAD
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)
=======
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF14274E), Color(0xFF2A60B0)))) // Fondo degradado
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
<<<<<<< HEAD
            // Sección superior con botón de flecha y botón de tema
=======
            // Sección superior con botón de catálogo y de tema
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
<<<<<<< HEAD
                // Botón con flecha para regresar al catálogo
                IconButton(
                    onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_DETAIL_SCREEN) },
                    modifier = Modifier
                        .size(48.dp) // Tamaño del botón
                        .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF00A9F4),
                                    Color(0xFF3F86F4)
                                ) // Degradado de colores
                            )
                        )
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver al Catálogo",
                        tint = Color.White // Flecha en color blanco
                    )
=======
                // Botón para volver al catálogo
                Button(
                    onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_DETAIL_SCREEN) },
                    modifier = Modifier
                        .defaultMinSize(minHeight = 48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2A60B0))
                ) {
                    Text(text = "Volver al Catálogo", color = Color.White)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                }

                // Botón para alternar el tema
                IconButton(onClick = { appState.toggleTheme() }) {
                    Icon(
                        imageVector = if (appState.isDarkTheme) Icons.Filled.Brightness4 else Icons.Filled.Brightness7,
<<<<<<< HEAD
                        contentDescription = "Cambiar Tema"
=======
                        contentDescription = "Cambiar Tema",
                        tint = Color.White
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            product?.let {
                // Imagen del producto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
<<<<<<< HEAD
                        .background(MaterialTheme.colors.surface),
=======
                        .background(Color(0xFF1B3F7D)),
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = it.imagen),
                        contentDescription = it.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
<<<<<<< HEAD
                            .clip(RoundedCornerShape(16.dp))
=======
                            .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para la imagen
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección de descripción
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
<<<<<<< HEAD
                    backgroundColor = MaterialTheme.colors.surface
=======
                    backgroundColor = Color(0xFF14274E)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Descripción",
<<<<<<< HEAD
                            color = MaterialTheme.colors.onSurface,
=======
                            color = Color.White,
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Left
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.nombre,
<<<<<<< HEAD
                            color = MaterialTheme.colors.onSurface,
=======
                            color = Color.White,
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = it.descripcion,
<<<<<<< HEAD
                            color = MaterialTheme.colors.onSurface,
=======
                            color = Color.White,
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección de precio y botón
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
<<<<<<< HEAD
                        .background(MaterialTheme.colors.primary)
=======
                        .background(Color(0xFF1B3F7D))
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Precio
                        Text(
                            text = "$ ${it.precio}",
<<<<<<< HEAD
                            color = MaterialTheme.colors.onPrimary,
=======
                            color = Color.White,
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )

                        // Botón "Add to Cart"
                        Button(
<<<<<<< HEAD
                            onClick = {
                                // Acción para añadir al carrito
                                println("Producto añadido: ${product?.nombre}")
                                carritoViewModel.addProductToCart(it)

                                // Navegar a la pantalla del carrito después de añadir el producto
                                openAndPopUp("carrito", PRODUCT_DETAIL_SCREEN)
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .defaultMinSize(minHeight = 48.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
                        ) {
                            Text(text = "Add to Cart", color = MaterialTheme.colors.onSurface)
=======
                            onClick = { /* Acción para añadir al carrito */ },
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .defaultMinSize(minHeight = 48.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2A60B0))
                        ) {
                            Text(text = "Add to Cart", color = Color.White)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                        }
                    }
                }
            } ?: if (productNotFound) {
<<<<<<< HEAD
                Text(text = "Producto no encontrado", color = MaterialTheme.colors.onSurface)
            } else {
                Text(text = "Cargando...", color = MaterialTheme.colors.onSurface)
=======
                Text(text = "Producto no encontrado", color = Color.White)
            } else {
                Text(text = "Cargando...", color = Color.White)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
            }
        }
    }
}
