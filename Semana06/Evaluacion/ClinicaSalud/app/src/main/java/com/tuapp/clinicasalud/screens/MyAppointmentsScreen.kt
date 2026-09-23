package com.tuapp.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class Appointment(val doctor: String, val dateTime: String, val status: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppointmentsScreen(navController: NavController) {
    val appointments = listOf(
        Appointment("Dra. Ana Torres", "Viernes 27, 10:30 am", "Confirmada"),
        Appointment("Dr. Luis Vega", "Miércoles 15, 3:00 pm", "Completada")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis citas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(appointments) { appointment ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(appointment.doctor, fontWeight = FontWeight.Bold)
                        Text(appointment.dateTime)
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = if (appointment.status == "Confirmada")
                                Color(0xFF4CAF50).copy(alpha = 0.2f)
                            else Color(0xFF9E9E9E).copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                appointment.status,
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