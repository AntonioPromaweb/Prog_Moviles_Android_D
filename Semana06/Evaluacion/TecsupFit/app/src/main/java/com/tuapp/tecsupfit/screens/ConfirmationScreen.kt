package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.tecsupfit.navigation.Screen

@Composable
fun ConfirmationScreen(navController: NavController, classId: Int) {
    val classes = listOf("Yoga funcional", "Cross Training", "Spinning")
    val className = classes.getOrNull(classId) ?: classes[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = Color(0xFFB2DFDB),
            modifier = Modifier.size(96.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier.padding(24.dp),
                tint = Color(0xFF00695C)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "¡Cupo reservado!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(className, style = MaterialTheme.typography.bodyLarge)
        Text("Hoy, 6:00 pm - Sala 1", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate(Screen.Reservations.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
        ) {
            Text("Ver mis reservas", color = Color.White)
        }
    }
}