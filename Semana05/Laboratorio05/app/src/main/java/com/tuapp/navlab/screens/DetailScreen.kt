package com.tuapp.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuapp.navlab.ui.theme.AcademicCardGray
import com.tuapp.navlab.ui.theme.AcademicDarkPurple
import com.tuapp.navlab.ui.theme.AcademicLightPurple
import com.tuapp.navlab.ui.theme.AcademicPurple
import com.tuapp.navlab.ui.theme.AcademicWhite

data class StudentDetail(
    val name: String,
    val career: String,
    val email: String,
    val id: String,
    val faculty: String,
    val biography: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: Int) {
    val detailsMap = mapOf(
        1 to StudentDetail(
            "Juan León",
            "Ingeniería de Sistemas",
            "juan.leon@example.com",
            "2024-0001",
            "Ingeniería y Tecnología",
            "Estudiante destacado con interés en desarrollo Android y arquitectura de software."
        ),
        2 to StudentDetail(
            "María García",
            "Arquitectura",
            "maria.garcia@example.com",
            "2024-0002",
            "Arquitectura y Urbanismo",
            "Apasionada por el diseño sostenible, la planificación urbana moderna y el modelado 3D."
        ),
        3 to StudentDetail(
            "Carlos Pérez",
            "Medicina",
            "carlos.perez@example.com",
            "2024-0003",
            "Ciencias de la Salud",
            "Dedicado a la investigación clínica y la atención médica comunitaria."
        ),
        4 to StudentDetail(
            "Ana López",
            "Derecho",
            "ana.lopez@example.com",
            "2024-0004",
            "Ciencias Sociales y Jurídicas",
            "Especializada en derecho corporativo y resolución alternativa de conflictos."
        ),
        5 to StudentDetail(
            "Luis Ramírez",
            "Administración",
            "luis.ramirez@example.com",
            "2024-0005",
            "Negocios y Gestión",
            "Enfocado en innovación empresarial y gestión estratégica de proyectos."
        )
    )

    val student = detailsMap[itemId] ?: detailsMap[1]!!

    Scaffold(
        containerColor = AcademicWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Expediente Académico",
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
            // Cabecera grande con degradado morado y esquinas inferiores redondeadas
            // Avatar circular superpuesto parcialmente entre la cabecera y el fondo blanco
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 45.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(AcademicLightPurple, AcademicDarkPurple)
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 45.dp)
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(AcademicWhite)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(AcademicPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.name.take(1),
                        color = AcademicWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = student.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = student.career,
                    fontSize = 15.sp,
                    color = AcademicPurple,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tarjeta gris con filas de datos
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = AcademicCardGray),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AcademicPurple.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Badge,
                                    contentDescription = "ID",
                                    tint = AcademicPurple,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(text = "ID Estudiante", fontSize = 11.sp, color = Color.Gray)
                                Text(
                                    text = student.id,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AcademicPurple.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = AcademicPurple,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(text = "Correo Electrónico", fontSize = 11.sp, color = Color.Gray)
                                Text(
                                    text = student.email,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AcademicPurple.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = "Facultad",
                                    tint = AcademicPurple,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(text = "Facultad", fontSize = 11.sp, color = Color.Gray)
                                Text(
                                    text = student.faculty,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        HorizontalDivider(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Column {
                            Text(
                                text = "Biografía",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = student.biography,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
