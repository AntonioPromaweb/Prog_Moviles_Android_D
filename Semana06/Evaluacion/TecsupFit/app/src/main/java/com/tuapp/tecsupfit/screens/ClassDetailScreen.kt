package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.tecsupfit.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(navController: NavController, classId: Int) {
    val classes = listOf(
        Triple("Yoga funcional", "7:00 am - Sala 2 - 45 min", "8 de 12 cupos disponibles"),
        Triple("Cross Training", "6:00 pm - Sala 1 - 45 min", "8 de 12 cupos disponibles"),
        Triple("Spinning", "7:30 pm - Sala 3 - 45 min", "5 de 10 cupos disponibles")
    )
    val gymClass = classes.getOrNull(classId) ?: classes[0]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de clase") },
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
            Surface(
                shape = MaterialTheme.shapes.large,
                color = Color(0xFFB2DFDB),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Icon(
                    Icons.Filled.FitnessCenter,
                    contentDescription = null,
                    tint = Color(0xFF00695C),
                    modifier = Modifier.padding(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(gymClass.first, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(gymClass.second, color = Color(0xFF00695C))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Entrenamiento funcional de alta intensidad. Cupos limitados.")
            Spacer(modifier = Modifier.height(8.dp))
            Text(gymClass.third, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate(Screen.Confirmation.createRoute(classId)) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00695C))
            ) {
                Text("Reservar cupo", color = Color.White)
            }
        }
    }
}