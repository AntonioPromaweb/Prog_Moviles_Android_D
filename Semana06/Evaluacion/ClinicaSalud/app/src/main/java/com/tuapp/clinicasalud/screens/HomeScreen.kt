package com.tuapp.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuapp.clinicasalud.navigation.Screen
import kotlinx.coroutines.launch

data class Doctor(val id: Int, val name: String, val specialty: String, val rating: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val doctors = listOf(
        Doctor(1, "Dra. Ana Torres", "Cardiología", 4.9),
        Doctor(2, "Dr. Luis Vega", "Pediatría", 4.7),
        Doctor(3, "Dra. Rosa Díaz", "Dermatología", 4.8)
    )
    val specialties = listOf("Cardiología", "Pediatría", "Dermatología")
    var selectedSpecialty by remember { mutableStateOf(specialties.first()) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
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
                            Text("JP", color = Color(0xFF673AB7), fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Juan Pérez", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Paciente", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Mis citas") },
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.MyAppointments.route)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Historial médico") },
                    icon = { Icon(Icons.Filled.History, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.MedicalHistory.route)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text("Clínica Salud+", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Hola, Juan", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF673AB7))
                )
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(specialties) { specialty ->
                        FilterChip(
                            selected = specialty == selectedSpecialty,
                            onClick = { selectedSpecialty = specialty },
                            label = { Text(specialty, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF673AB7),
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFF5F5F5)
                            )
                        )
                    }
                }

                Text(
                    "Médicos disponibles",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(doctors) { index, doctor ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { navController.navigate(Screen.DoctorProfile.createRoute(index)) },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = MaterialTheme.shapes.medium,
                                    color = Color(0xFFE8DEF8),
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.Add,
                                        contentDescription = null,
                                        tint = Color(0xFF673AB7),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(doctor.name, fontWeight = FontWeight.Bold)
                                    Text(doctor.specialty, color = Color(0xFF673AB7), style = MaterialTheme.typography.bodyMedium)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                                    Text(" ${doctor.rating}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}