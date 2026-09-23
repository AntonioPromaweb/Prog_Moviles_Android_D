package com.tuapp.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuapp.navlab.navigation.Screen
import com.tuapp.navlab.ui.theme.AcademicBackground
import com.tuapp.navlab.ui.theme.AcademicDarkPurple
import com.tuapp.navlab.ui.theme.AcademicLightPurple
import com.tuapp.navlab.ui.theme.AcademicLightRed
import com.tuapp.navlab.ui.theme.AcademicPurple
import com.tuapp.navlab.ui.theme.AcademicRed
import com.tuapp.navlab.ui.theme.AcademicWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        containerColor = AcademicWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Configuración de Perfil",
                        color = AcademicDarkPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = AcademicPurple
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AcademicWhite)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Panel con degradado morado completo (avatar totalmente contenido)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(AcademicLightPurple, AcademicDarkPurple)
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(AcademicWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LV",
                            color = AcademicPurple,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Luis Vasquez",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AcademicWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                // Sección INFORMACIÓN PERSONAL
                Text(
                    text = "INFORMACIÓN PERSONAL",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AcademicPurple
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItemRow(
                    icon = Icons.Default.Person,
                    label = "Nombre Completo",
                    value = "Luis Vasquez"
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItemRow(
                    icon = Icons.Default.Email,
                    label = "Correo",
                    value = "luis.vasquez@tecsup.edu.pe"
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItemRow(
                    icon = Icons.Default.Phone,
                    label = "Teléfono",
                    value = "+51 987 654 321"
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sección ACADÉMICO
                Text(
                    text = "ACADÉMICO",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AcademicPurple
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItemRow(
                    icon = Icons.Default.School,
                    label = "Carrera",
                    value = "Ingeniería de Software"
                )
                Spacer(modifier = Modifier.height(12.dp))

                ProfileItemRow(
                    icon = Icons.Default.Book,
                    label = "Ciclo Actual",
                    value = "VI Ciclo"
                )

                Spacer(modifier = Modifier.weight(1f))

                // Botón "Cerrar Sesión" anclado abajo con fondo rosa claro
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clickable {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = AcademicLightRed),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión",
                            tint = AcademicRed
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cerrar Sesión",
                            color = AcademicRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ProfileItemRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AcademicBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = AcademicPurple,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
