package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuapp.tecsupfit.navigation.Screen

data class GymClass(val id: Int, val name: String, val schedule: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val classes = listOf(
        GymClass(1, "Yoga funcional", "7:00 am - Sala 2"),
        GymClass(2, "Cross Training", "6:00 pm - Sala 1"),
        GymClass(3, "Spinning", "7:30 pm - Sala 3")
    )
    val filters = listOf("Hoy", "Esta semana")
    var selectedFilter by remember { mutableStateOf(filters.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("TECSUP Fit", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Hola, Luis", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00695C))
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                    label = { Text("Reservas") },
                    selected = false,
                    onClick = { navController.navigate(Screen.Reservations.route) }
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
        Column(modifier = Modifier.padding(padding)) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        selected = filter == selectedFilter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF00695C),
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF5F5F5)
                        )
                    )
                }
            }

            Text(
                "Clases disponibles",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(classes) { index, gymClass ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(Screen.ClassDetail.createRoute(index)) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = MaterialTheme.shapes.medium,
                                color = Color(0xFFB2DFDB),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    Icons.Filled.FitnessCenter,
                                    contentDescription = null,
                                    tint = Color(0xFF00695C),
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(gymClass.name, fontWeight = FontWeight.Bold)
                                Text(gymClass.schedule, color = Color(0xFF00695C), style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}