package com.tuapp.navlab.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppDrawer(navController: NavController, onItemClick: (String) -> Unit) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = Color(0xFFE8DEF8),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("MR", color = Color(0xFF673AB7), fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("María Rojas", fontWeight = FontWeight.Bold)
                Text("maria@tecsup.edu.pe", style = MaterialTheme.typography.bodySmall)
            }
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        NavigationDrawerItem(
            label = { Text("Inicio") },
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            selected = true,
            onClick = {
                navController.navigate("store") {
                    popUpTo("store") { inclusive = true }
                }
                onItemClick("inicio")
            }
        )
        NavigationDrawerItem(
            label = { Text("Mis pedidos") },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
            selected = false,
            onClick = {
                navController.navigate("misPedidos")
                onItemClick("pedidos")
            }
        )
        NavigationDrawerItem(
            label = { Text("Favoritos") },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            selected = false,
            onClick = {
                navController.navigate("favoritos")
                onItemClick("favoritos")
            }
        )
        NavigationDrawerItem(
            label = { Text("Perfil") },
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            selected = false,
            onClick = {
                navController.navigate("perfil")
                onItemClick("perfil")
            }
        )
        NavigationDrawerItem(
            label = { Text("Cerrar sesión") },
            icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
            selected = false,
            onClick = { onItemClick("cerrar") }
        )
    }
}