package com.example.compusnow.screens.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CatalogScreen(
    openAndPopUp: (String, String) -> Unit
) {
    val viewModel: CatalogViewModel = hiltViewModel()
    val catalogItems = viewModel.catalogItems

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Botón para cerrar sesión en la parte superior
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    viewModel.signOut(openAndPopUp) // Llamamos al método para cerrar sesión
                }
            ) {
                Text("Cerrar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Título del catálogo
        Text(
            text = "Catálogo",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de productos en el catálogo
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(catalogItems) { item ->
                CatalogItem(item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar un nuevo producto al catálogo
        Button(
            onClick = {
                // Acción para añadir un nuevo producto o navegar a otra pantalla
                openAndPopUp("NewProductScreen", "CatalogScreen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Añadir Producto")
        }
    }
}

@Composable
fun CatalogItem(item: String) {
    // Mostramos el item del catálogo
    Text(
        text = item,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    CatalogScreen(openAndPopUp = { _, _ -> })
}
