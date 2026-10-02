package com.tuapp.navlab.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.navlab.model.Categoria
import com.tuapp.navlab.model.Producto
import com.tuapp.navlab.model.categorias
import com.tuapp.navlab.model.productos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(navController: NavController) {
    var categoriaSeleccionada by remember { mutableStateOf("Todas") }

    val productosFiltrados = if (categoriaSeleccionada == "Todas") {
        productos
    } else {
        productos.filter { it.categoria == categoriaSeleccionada }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TECSUP Store", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Drawer aquí después */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                "Más vendidos",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = categoriaSeleccionada == "Todas",
                        onClick = { categoriaSeleccionada = "Todas" },
                        label = { Text("Todas") }
                    )
                }
                items(categorias) { categoria ->
                    FilterChip(
                        selected = categoriaSeleccionada == categoria.nombre,
                        onClick = { categoriaSeleccionada = categoria.nombre },
                        label = { Text(categoria.nombre) }
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(productosFiltrados) { producto ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(producto.nombre, fontWeight = FontWeight.Bold)
                            Text("S/ ${producto.precio}", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}