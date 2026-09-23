package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.tecsupfit.navigation.Screen

data class Reservation(val className: String, val dateTime: String, val status: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(navController: NavController) {
    val reservations = listOf(
        Reservation("Cross Training", "Hoy, 6:00 pm", "Confirmada"),
        Reservation("Yoga funcional", "Ayer, 7:00 am", "Completada")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis reservas") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00695C))
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Home.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                    label = { Text("Reservas") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = null) },
                    label = { Text("Rutinas") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Routines.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(reservations) { reservation ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(reservation.className, fontWeight = FontWeight.Bold)
                        Text(reservation.dateTime)
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = if (reservation.status == "Confirmada")
                                Color(0xFF4CAF50).copy(alpha = 0.2f)
                            else Color(0xFF9E9E9E).copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                reservation.status,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}