package com.tuapp.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.clinicasalud.navigation.Screen

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clínica Salud+") },
                navigationIcon = {
                    IconButton(onClick = { /* Abrir drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(specialties) { specialty ->
                    FilterChip(
                        selected = specialty == selectedSpecialty,
                        onClick = { selectedSpecialty = specialty },
                        label = { Text(specialty) }
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(doctors) { index, doctor ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(Screen.DoctorProfile.createRoute(index)) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(doctor.name, fontWeight = FontWeight.Bold)
                            Text(doctor.specialty, color = MaterialTheme.colorScheme.primary)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107))
                                Text(" ${doctor.rating}")
                            }
                        }
                    }
                }
            }
        }
    }
}