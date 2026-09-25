package com.tuapp.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

data class Reservation(val className: String, val dateTime: String, val status: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(navController: NavController) {
    val reservations = listOf(
        Reservation("Cross Training", "Hoy, 6:00 pm", "Confirmada"),
        Reservation("Yoga funcional", "Ayer, 7:00 am", "Completada")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis reservas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdePrimary)
            )
        },
        bottomBar = {
            AppBottomBar(navController = navController, currentRoute = Screen.Reservations.route)
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
            items(reservations) { reservation ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardFondo),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .fillMaxHeight()
                                .background(
                                    if (reservation.status == "Confirmada") VerdePrimary else Color.Gray
                                )
                        )
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = reservation.className,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Surface(
                                    color = if (reservation.status == "Confirmada") VerdeLight else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = reservation.status,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        color = if (reservation.status == "Confirmada") VerdePrimary else Color(0xFF616161),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = reservation.dateTime,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
