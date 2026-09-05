package com.example.laboratorio03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio03.ui.theme.Laboratorio03Theme
import kotlin.math.roundToInt

class TareaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio03Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaRegistroNotas(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaRegistroNotas(modifier: Modifier = Modifier) {
    var nota1 by remember { mutableFloatStateOf(0f) }
    var nota2 by remember { mutableFloatStateOf(0f) }
    var nota3 by remember { mutableFloatStateOf(0f) }
    var nota4 by remember { mutableFloatStateOf(0f) }

    var redondear by remember { mutableStateOf(false) }
    var confirmacion by remember { mutableStateOf(false) }

    var mostrarResultado by remember { mutableStateOf(false) }
    var promPonderado by remember { mutableDoubleStateOf(0.0) }
    var promFinalStr by remember { mutableStateOf("") }
    var observacionText by remember { mutableStateOf("") }
    var chipColor by remember { mutableStateOf(Color.Gray) }

    val moradoEncabezado = Color(0xFF6750A4)
    val moradoBoton = Color(0xFF6750A4)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF3EDF7), Color(0xFFE6DEEA))
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(moradoEncabezado)
                .padding(20.dp)
        ) {
            Text(
                text = "Registro de Notas",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Notas del ciclo", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                "Desliza para asignar cada nota (0 a 20)",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilaCurso("Fundamentos de Programación (20%)", nota1) { nota1 = it }
            FilaCurso("Programación Orientada a Objetos (25%)", nota2) { nota2 = it }
            FilaCurso("Programación en Móviles (30%)", nota3) { nota3 = it }
            FilaCurso("Base de Datos (25%)", nota4) { nota4 = it }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Redondear promedio final", fontSize = 14.sp)
                Switch(
                    checked = redondear,
                    onCheckedChange = { redondear = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = confirmacion,
                    onCheckedChange = { confirmacion = it }
                )
                Text("Confirmo que las notas son correctas", fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val p = (nota1 * 0.20 + nota2 * 0.25 + nota3 * 0.30 + nota4 * 0.25).toDouble()
                    promPonderado = p

                    val valFinal: Double = if (redondear) p.roundToInt().toDouble() else p
                    promFinalStr = if (redondear) "${p.roundToInt()}" else String.format("%.2f", p)

                    when {
                        valFinal >= 17.0 -> {
                            observacionText = "EXCELENTE"
                            chipColor = Color(0xFF1B5E20)
                        }
                        valFinal >= 13.0 -> {
                            observacionText = "APROBADO"
                            chipColor = Color(0xFF2E7D32)
                        }
                        valFinal >= 10.0 -> {
                            observacionText = "EN RECUPERACIÓN"
                            chipColor = Color(0xFFF57C00)
                        }
                        else -> {
                            observacionText = "DESAPROBADO"
                            chipColor = Color(0xFFC62828)
                        }
                    }

                    mostrarResultado = true
                },
                enabled = confirmacion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = moradoBoton)
            ) {
                Text("CALCULAR PROMEDIO", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!mostrarResultado) {
                Text(
                    text = "Asigna las notas y confirma para calcular",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Promedio ponderado:  ${String.format("%.2f", promPonderado)}",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                "Promedio final:  $promFinalStr",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4A148C),
                                fontSize = 18.sp
                            )
                            if (redondear) {
                                Text(
                                    " (redondeado)",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = chipColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = observacionText,
                                color = chipColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "✓ Promedio calculated correctamente",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Desarrollado por: Antonio",
                color = Color.Gray,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun FilaCurso(nombre: String, valor: Float, onValorChange: (Float) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(nombre, fontWeight = FontWeight.Medium, fontSize = 13.sp)
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFEADDFF)
            ) {
                Text(
                    text = "${valor.toInt()}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                    fontSize = 13.sp
                )
            }
        }
        Slider(
            value = valor,
            onValueChange = onValorChange,
            valueRange = 0f..20f,
            steps = 19
        )
    }
}