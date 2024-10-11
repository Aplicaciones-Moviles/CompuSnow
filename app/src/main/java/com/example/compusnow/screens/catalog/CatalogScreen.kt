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
import androidx.compose.material.IconButton
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.CompuSnowAppState
import com.example.compusnow.PRODUCT_EDIT_SCREEN
import com.example.compusnow.PRODUCT_DETAIL_SCREEN
import com.example.compusnow.PRODUCT_SCREEN
import com.example.compusnow.R
import com.example.compusnow.model.Product
import com.example.compusnow.screens.carrito.CarritoViewModel
import com.example.compusnow.screens.catalog.CatalogViewModel
import com.example.compusnow.screens.productEdit.ProductEditViewModel

@Composable
fun CatalogScreen(
    openAndPopUp: (String, String) -> Unit,
    appState: CompuSnowAppState
) {
<<<<<<< HEAD
    val carritoViewModel: CarritoViewModel = hiltViewModel()
=======
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
    val productEditViewModel: ProductEditViewModel = hiltViewModel()
    val viewModel: CatalogViewModel = hiltViewModel()
    val products by viewModel.products.collectAsState(emptyList()) // Observa el flujo de productos
    val isLoading = products.isEmpty()

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
    // Estado para controlar el menú desplegable
    var expanded by remember { mutableStateOf(false) }

=======
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)  // Aplicar fondo dependiendo del tema
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sección de botón de cambio de tema y menú desplegable
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
<<<<<<< HEAD
                // Menú hamburguesa para mostrar los filtros y la opción de cerrar sesión
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Mostrar filtros",
                        tint = if (!appState.isDarkTheme) Color.Black else MaterialTheme.colors.onPrimary // Cambia a negro en modo claro
                    )
=======
                // Botón de cambio de tema
                IconButton(onClick = { appState.toggleTheme() }) {
                    Icon(
                        imageVector = Icons.Default.Brightness4,
                        contentDescription = "Cambiar Tema",
                        tint = MaterialTheme.colors.onPrimary  // Usa el color según el tema
                    )
                }
                // Filtros con iconos de Material y cuadros alrededor
                Row {
                    FilterButton("All", Icons.Default.AllInclusive)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterButton("PC", Icons.Default.Computer)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterButton("Laptop", Icons.Default.Laptop)
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterButton("GPU", Icons.Default.Memory)
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                }

                // Menú desplegable con los filtros y la opción de cerrar sesión
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
<<<<<<< HEAD
                    DropdownMenuItem(onClick = { /* Acción para filtrar por "All" */ }) {
                        FilterButton("All", Icons.Default.AllInclusive)
                    }
                    DropdownMenuItem(onClick = { /* Acción para filtrar por "PC" */ }) {
                        FilterButton("PC", Icons.Default.Computer)
                    }
                    DropdownMenuItem(onClick = { /* Acción para filtrar por "Laptop" */ }) {
                        FilterButton("Laptop", Icons.Default.Laptop)
                    }
                    DropdownMenuItem(onClick = { /* Acción para filtrar por "GPU" */ }) {
                        FilterButton("GPU", Icons.Default.Memory)
                    }
                    Divider() // Separador para distinguir los filtros de la opción de cerrar sesión
                    DropdownMenuItem(onClick = { viewModel.signOut(openAndPopUp) }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Cerrar Sesión",
                                tint = MaterialTheme.colors.onSurface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Cerrar Sesión", color = MaterialTheme.colors.onSurface)
                        }
                    }
                }

                // Botón de cambio de tema en la parte derecha
                Row {
                    IconButton(onClick = { appState.toggleTheme() }) {
                        Icon(
                            imageVector = Icons.Default.Brightness4,
                            contentDescription = "Cambiar Tema",
                            tint = if (!appState.isDarkTheme) Color.Black else MaterialTheme.colors.onPrimary // Cambia a negro en modo claro
                        )
                    }

                    // Botón de carrito de compras (Nuevo)
                    IconButton(onClick = { appState.navigate("carrito") }) { // Aquí usamos navigate para ir a la pantalla del carrito
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito de Compras",
                            tint = if (!appState.isDarkTheme) Color.Black else MaterialTheme.colors.onPrimary
                        )
                    }
=======
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Cerrar Sesión",
                        tint = MaterialTheme.colors.onPrimary // Usa el color según el tema
                    )
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de promociones
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.primary)  // Usa el color primario del tema
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
                        Text(text = "30% Off", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onPrimary)
                        Text(text = "En productos seleccionados", color = MaterialTheme.colors.onPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar mensaje o animación si no hay productos cargados
            if (isLoading) {
                Text(text = "Cargando productos...", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onPrimary)
            } else if (products.isEmpty()) {
                Text(text = "No hay productos disponibles", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onPrimary)
            } else {
                // Lista de productos en grilla con tamaño uniforme
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
                            onDelete = {
                                // Navegar a la pantalla de edición para eliminar el producto
                                productEditViewModel.deleteProduct(product.id)
                            },
                            onUpdate = {
                                // Navegar a la pantalla de edición para actualizar el producto
                                viewModel.navigateToEditProduct(product.id, openAndPopUp)
                            },
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
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)  // Usa el color surface del tema
            ) {
                Text(text = "Añadir Producto", color = MaterialTheme.colors.onSurface)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onDelete: () -> Unit, onUpdate: () -> Unit, onProductClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.surface) // Usa el color surface del tema para el fondo del cuadro
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp) // Establece una altura fija para todas las tarjetas
            .clickable { onProductClick(product.id) }
    ) {
        // Box con imagen con relación de aspecto fija
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // Relación de aspecto 1:1 para que todas las imágenes sean cuadradas
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = rememberImagePainter(data = product.imagen),
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para la imagen
            )

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = MaterialTheme.colors.onSurface // Usa el color adecuado para los íconos
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onUpdate()
                }) {
                    Text("Actualizar", color = MaterialTheme.colors.onSurface) // Color de texto adecuado
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onDelete()
                }) {
                    Text("Eliminar", color = MaterialTheme.colors.onSurface) // Color de texto adecuado
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.nombre,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface // Usa el color de texto adecuado
        )
        Text(
            text = "Precio: \$${product.precio}",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface // Usa el color de texto adecuado
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Acción para agregar al carrito */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Agregar", color = MaterialTheme.colors.onPrimary)
        }
    }
}

@Composable
fun FilterButton(text: String, icon: ImageVector) {
    // Mostrar el ícono y el texto en una fila
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.surface) // Usa el color surface del tema
            .padding(8.dp)
            .clickable { /* Acción al hacer clic en el filtro */ }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colors.onSurface,
<<<<<<< HEAD
            modifier = Modifier.size(20.dp) // Tamaño ajustado del ícono
        )
        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el texto
        Text(text = text, color = MaterialTheme.colors.onSurface)
    }
}
@Composable
fun ProductCard(
    product: Product,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: CarritoViewModel = hiltViewModel() // ViewModel para manejar el carrito
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.surface)
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp)
            .clickable { onProductClick(product.id) }
    ) {
        // Imagen y opciones del producto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), // Relación de aspecto 1:1 para que todas las imágenes sean cuadradas
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = rememberImagePainter(data = product.imagen),
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas para la imagen
            )

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Más opciones",
                    tint = MaterialTheme.colors.onSurface // Usa el color adecuado para los íconos
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onUpdate()
                }) {
                    Text("Actualizar", color = MaterialTheme.colors.onSurface)
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    onDelete()
                }) {
                    Text("Eliminar", color = MaterialTheme.colors.onSurface)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.nombre,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface
        )
        Text(
            text = "Precio: \$${product.precio}",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón "Agregar" que añade el producto al carrito
        Button(
            onClick = {
                viewModel.addProductToCart(product) // Asegúrate de que esta función se esté llamando correctamente
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Agregar", color = MaterialTheme.colors.onPrimary)
        }
    }
}
=======
            modifier = Modifier.size(15.dp)
        )
        Text(text = text, color = MaterialTheme.colors.onSurface)
    }
}
>>>>>>> ffcc525dac5cf4f68eaa8708ee4bae404439b4d2

