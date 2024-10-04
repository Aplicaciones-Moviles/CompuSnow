package com.example.compusnow.screens.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.PRODUCT_DETAIL_SCREEN
import com.example.compusnow.PRODUCT_SCREEN
import com.example.compusnow.R
import com.example.compusnow.model.Product
import com.example.compusnow.screens.catalog.CatalogViewModel

@Composable
fun CatalogScreen(
    openAndPopUp: (String, String) -> Unit
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    val products by viewModel.products.collectAsState(emptyList()) // Observa el flujo de productos
    val isLoading = products.isEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF14274E), Color(0xFF2A60B0)))) // Fondo degradado
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Sección de filtros y botón de cerrar sesión
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Filtros con iconos de Material y cuadros alrededor
                Row {
                    FilterButton("All", Icons.Default.AllInclusive)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterButton("PC", Icons.Default.Computer)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterButton("Laptop", Icons.Default.Laptop)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterButton("GPU", Icons.Default.Memory)
                }

                // Icono para cerrar sesión
                IconButton(
                    onClick = {
                        viewModel.signOut(openAndPopUp)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Cerrar Sesión",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de promociones
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF2A60B0))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.laptop_promo), // Suponiendo una imagen de recurso
                        contentDescription = "Promoción",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = "30% Off", style = MaterialTheme.typography.h4, color = Color.White)
                        Text(text = "En productos seleccionados", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar mensaje o animación si no hay productos cargados
            if (isLoading) {
                Text(text = "Cargando productos...", style = MaterialTheme.typography.body1, color = Color.White)
            } else if (products.isEmpty()) {
                Text(text = "No hay productos disponibles", style = MaterialTheme.typography.body1, color = Color.White)
            } else {
                // Lista de productos en grilla
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f) // Permite que el catálogo se ajuste a la pantalla
                        .padding(bottom = 60.dp), // Espacio entre el final de la grilla y el botón
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product,
                            onDelete = { viewModel.deleteProduct(product.id) },
                            onUpdate = { viewModel.updateProduct(openAndPopUp, product.id) },
                            onProductClick = { productId ->
                                openAndPopUp("$PRODUCT_DETAIL_SCREEN/$productId", CATALOG_SCREEN)
                            }
                        )
                    }
                }
            }
        }

        // Botón fijo en la parte inferior fuera del espacio de los productos
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp), // Asegura que no cubra la barra inferior del sistema
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    openAndPopUp(PRODUCT_SCREEN, CATALOG_SCREEN)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
            ) {
                Text(text = "Añadir Producto", color = Color.White)
            }
        }
    }
}

@Composable
fun FilterButton(text: String, icon: ImageVector) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF14274E)) // Fondo del filtro con color
            .padding(8.dp) // Espaciado interno
            .clickable { /* Acción al hacer clic en el filtro */ }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(15.dp)
        )
        Text(text = text, color = Color.White)
    }
}

@Composable
fun ProductCard(product: Product, onDelete: () -> Unit, onUpdate: () -> Unit, onProductClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) } // Estado para controlar la expansión del menú

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF14274E))
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onProductClick(product.id) } // Redirigir a la pantalla de detalles
    ) {
        // Cargar la imagen desde la URL del producto
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd // Para alinear los tres puntos en la parte superior derecha
        ) {
            Image(
                painter = rememberImagePainter(data = product.imagen),
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para la imagen
            )

            // Icono de tres puntos para más opciones
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = Color.White
                )
            }

            // Menú desplegable con las opciones de eliminar y actualizar
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onUpdate() // Llamar a la función de actualizar
                }) {
                    Text("Actualizar")
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onDelete() // Llamar a la función de eliminar
                }) {
                    Text("Eliminar")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar el nombre del producto
        Text(text = product.nombre, style = MaterialTheme.typography.h6, color = Color.White)

        // Mostrar el precio del producto
        Text(text = "Precio: \$${product.precio}", style = MaterialTheme.typography.body1, color = Color.White)

        // Botón para agregar a carrito
        Button(
            onClick = { /* Acción para agregar al carrito */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1B3F7D))
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Agregar", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    CatalogScreen(openAndPopUp = { _, _ -> })
}
