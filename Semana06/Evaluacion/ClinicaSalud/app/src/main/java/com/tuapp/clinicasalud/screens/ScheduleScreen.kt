package com.tuapp.clinicasalud.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.clinicasalud.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(navController: NavController, doctorId: Int) {
    val dates = listOf("Jue 26", "Vie 27", "Sáb 28")
    val times = listOf("9:00", "10:30", "3:00")
    var selectedDate by remember { mutableStateOf(dates[1]) }
    var selectedTime by remember { mutableStateOf(times[1]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
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
                .fillMaxSize()
        ) {
            Text("Selecciona fecha", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                dates.forEach { date ->
                    FilterChip(
                        selected = date == selectedDate,
                        onClick = { selectedDate = date },
                        label = { Text(date) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Selecciona hora", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                times.forEach { time ->
                    FilterChip(
                        selected = time == selectedTime,
                        onClick = { selectedTime = time },
                        label = { Text(time) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(
                        Screen.Confirmation.createRoute(doctorId, selectedDate, selectedTime)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar cita")
            }
        }
    }
}