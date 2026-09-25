package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuapp.tecsupfit.navigation.Screen
import com.tuapp.tecsupfit.ui.theme.CardFondo
import com.tuapp.tecsupfit.ui.theme.FondoClaro
import com.tuapp.tecsupfit.ui.theme.VerdeLight
import com.tuapp.tecsupfit.ui.theme.VerdePrimary

data class RoutineItem(val day: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(navController: NavController) {
    val routines = listOf(
        RoutineItem("Lunes", "Pecho y tríceps"),
        RoutineItem("Martes", "Espalda y bíceps"),
        RoutineItem("Miércoles", "Pierna y abdomen"),
        RoutineItem("Jueves", "Hombros y trapecio"),
        RoutineItem("Viernes", "Cardio y full body")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Rutinas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrimary)
            )
        },
        bottomBar = {
            AppBottomBar(navController = navController, currentRoute = Screen.Routines.route)
        },
        containerColor = FondoClaro
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(routines) { routine ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardFondo),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = VerdeLight,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.FitnessCenter,
                                    contentDescription = null,
                                    tint = VerdePrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "${routine.day}: ${routine.description}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
