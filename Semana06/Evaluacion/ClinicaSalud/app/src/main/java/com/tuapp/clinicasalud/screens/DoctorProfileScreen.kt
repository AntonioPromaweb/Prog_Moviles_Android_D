package com.tuapp.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.clinicasalud.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorProfileScreen(navController: NavController, doctorId: Int) {
    val doctors = listOf(
        Triple("Dra. Ana Torres", "Cardiología", 4.9),
        Triple("Dr. Luis Vega", "Pediatría", 4.7),
        Triple("Dra. Rosa Díaz", "Dermatología", 4.8)
    )
    val doctor = doctors.getOrNull(doctorId) ?: doctors[0]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del médico") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(doctor.first, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(doctor.second, color = MaterialTheme.colorScheme.primary)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107))
                Text(" ${doctor.third} (128 reseñas)")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Especialista en arritmias e hipertensión. Formación en la Clínica Mayo.",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate(Screen.Schedule.createRoute(doctorId)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar cita")
            }
        }
    }
}