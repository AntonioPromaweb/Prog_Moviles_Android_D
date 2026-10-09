package com.tuapp.citas.ui.screens.agendamiento

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.BarraSuperiorConVolver
import com.tuapp.citas.ui.components.BotonPrimario
import com.tuapp.citas.ui.components.FotoMedico
import com.tuapp.citas.ui.theme.*
import com.tuapp.citas.util.diasHabilesDeSemana
import com.tuapp.citas.util.mesYAnio
import com.tuapp.citas.util.nombreCortoDia
import java.time.LocalDate

private const val MAX_SEMANAS_ADELANTE = 12

@Composable
fun FechaHoraScreen(
    medicoId: String,
    alContinuar: (String, String) -> Unit,
    alVolver: () -> Unit
) {
    val medico = remember(medicoId) { Repositorio.obtenerMedico(medicoId) }
    val especialidad = remember(medico) {
        medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    }

    // Calendario dinámico: semana mostrada, día y hora elegidos
    val hoy = remember { LocalDate.now() }
    var semanaOffset by remember { mutableIntStateOf(0) }
    val dias = remember(semanaOffset) { diasHabilesDeSemana(semanaOffset, hoy) }
    var diaSeleccionado by remember { mutableStateOf(dias.first()) }
    var horaSeleccionada by remember { mutableStateOf<String?>(null) }

    // Fecha en formato ISO (yyyy-MM-dd) para el repositorio y la navegación
    val fechaCompleta = diaSeleccionado.toString()

    // Los horarios se recalculan solos al cambiar de médico o de día
    val horariosDisponibles = remember(medicoId, fechaCompleta) {
        if (medico == null) emptyList() else Repositorio.horariosDisponibles(medicoId, fechaCompleta)
    }

    Scaffold(
        containerColor = Blanco,
        topBar = {
            BarraSuperiorConVolver(
                titulo = "Seleccionar fecha y hora",
                alVolver = alVolver
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Blanco
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    BotonPrimario(
                        texto = "Continuar",
                        habilitado = horaSeleccionada != null,
                        onClick = {
                            horaSeleccionada?.let { hora ->
                                alContinuar(fechaCompleta, hora)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Blanco)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Tarjeta superior resumen del médico
            medico?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AzulClaro.copy(alpha = 0.7f), RoundedCornerShape(14.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FotoMedico(fotoRes = it.fotoRes, tamano = 64.dp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(it.nombre, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = AzulOscuro)
                        Text(it.cargo.ifEmpty { especialidad?.nombre ?: "" }, fontSize = 14.sp, color = TextoGris)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Mes y año de la semana mostrada + flechas de semana
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = semanaOffset > 0,
                    onClick = {
                        semanaOffset--
                        diaSeleccionado = diasHabilesDeSemana(semanaOffset, hoy).first()
                        horaSeleccionada = null
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Semana anterior",
                        tint = if (semanaOffset > 0) AzulOscuro else BordeGris
                    )
                }
                Text(
                    text = mesYAnio(dias.first()),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AzulOscuro
                )
                IconButton(
                    enabled = semanaOffset < MAX_SEMANAS_ADELANTE,
                    onClick = {
                        semanaOffset++
                        diaSeleccionado = diasHabilesDeSemana(semanaOffset, hoy).first()
                        horaSeleccionada = null
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Semana siguiente",
                        tint = AzulOscuro
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Fila de días hábiles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dias.forEach { dia ->
                    val esSeleccionado = diaSeleccionado == dia
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (esSeleccionado) AzulPrimario else FondoCampo)
                            .clickable {
                                diaSeleccionado = dia
                                horaSeleccionada = null // Reiniciar hora al cambiar día
                            }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = nombreCortoDia(dia),
                            fontSize = 12.sp,
                            color = if (esSeleccionado) Blanco else TextoGris
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = dia.dayOfMonth.toString(),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (esSeleccionado) Blanco else AzulOscuro
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (medico == null) {
                Text("No se encontró el médico seleccionado.", color = TextoGris, fontSize = 14.sp)
            } else if (horariosDisponibles.isEmpty()) {
                Text("No hay horarios disponibles para este día.", color = TextoGris, fontSize = 14.sp)
            }

            // Cuadrícula de horarios (LazyVerticalGrid)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(horariosDisponibles) { hora ->
                    val estaSeleccionada = horaSeleccionada == hora
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (estaSeleccionada) AzulPrimario else FondoCampo)
                            .border(
                                width = 1.dp,
                                color = if (estaSeleccionada) AzulPrimario else BordeGris,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { horaSeleccionada = hora }
                            .padding(vertical = 15.dp)
                    ) {
                        Text(
                            text = hora,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (estaSeleccionada) Blanco else AzulOscuro
                        )
                    }
                }
            }
        }
    }
}
