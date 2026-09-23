package com.tuapp.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.clinicasalud.navigation.Screen

@Composable
fun ConfirmationScreen(navController: NavController, doctorId: Int, date: String, time: String) {
    val doctors = listOf("Dra. Ana Torres", "Dr. Luis Vega", "Dra. Rosa Díaz")
    val doctorName = doctors.getOrNull(doctorId) ?: doctors[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Cita agendada!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(doctorName, style = MaterialTheme.typography.bodyLarge)
        Text("$date, $time", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver mis citas")
        }
    }
}