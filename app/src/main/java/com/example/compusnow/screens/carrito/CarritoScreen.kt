package com.example.compusnow.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // Importación necesaria para el uso de columnas y espaciado
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.* // Importación para MaterialTheme, Buttons, etc.
import androidx.compose.runtime.* // Importación para el manejo del estado
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.compusnow.CATALOG_SCREEN
import com.example.compusnow.PRODUCT_DETAIL_SCREEN

@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel = viewModel(),
    openAndPopUp: (String, String) -> Unit // Añadimos la función de navegación como parámetro
) {
    // Observar los flujos de datos del carrito y el precio total usando collectAsState
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Carrito de Compras", style = MaterialTheme.typography.h5)

        if (cartItems.isEmpty()) {
            Text(text = "El carrito está vacío.", style = MaterialTheme.typography.body1)
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

                            // Información del producto (nombre, descripción y precio)
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
                                    text = "$${product.precio}",
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold
                                )
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
