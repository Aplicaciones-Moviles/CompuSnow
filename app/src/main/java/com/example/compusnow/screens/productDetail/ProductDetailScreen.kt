package com.example.compusnow.screens.productDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.compusnow.screens.catalog.CatalogViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(
    productId: String,
    openAndPopUp: (String, String) -> Unit,
    navController: NavController,
    appState: CompuSnowAppState // Asegúrate de pasar el appState para el control del tema
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

    // Manejar el botón de retroceso
    BackHandler {
        if (!navController.popBackStack()) {
            navController.navigate(CATALOG_SCREEN)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF14274E), Color(0xFF2A60B0)))) // Fondo degradado
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Sección superior con botón de catálogo y de tema
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón para volver al catálogo
                Button(
                    onClick = { openAndPopUp(CATALOG_SCREEN, PRODUCT_DETAIL_SCREEN) },
                    modifier = Modifier
                        .defaultMinSize(minHeight = 48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2A60B0))
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

            Spacer(modifier = Modifier.height(16.dp))

            product?.let {
                // Imagen del producto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1B3F7D)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(data = it.imagen),
                        contentDescription = it.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para la imagen
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sección de descripción
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    backgroundColor = Color(0xFF14274E)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Descripción",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Left
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it.nombre,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = it.descripcion,
                            color = Color.White,
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
                        .background(Color(0xFF1B3F7D))
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
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )

                        // Botón "Add to Cart"
                        Button(
                            onClick = { /* Acción para añadir al carrito */ },
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .defaultMinSize(minHeight = 48.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2A60B0))
                        ) {
                            Text(text = "Add to Cart", color = Color.White)
                        }
                    }
                }
            } ?: if (productNotFound) {
                Text(text = "Producto no encontrado", color = Color.White)
            } else {
                Text(text = "Cargando...", color = Color.White)
            }
        }
    }
}
