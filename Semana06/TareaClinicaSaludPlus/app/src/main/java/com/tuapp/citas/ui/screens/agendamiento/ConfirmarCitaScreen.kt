package com.tuapp.citas.ui.screens.agendamiento

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.BarraSuperiorConVolver
import com.tuapp.citas.ui.components.BotonPrimario
import com.tuapp.citas.ui.components.FotoMedico
import com.tuapp.citas.ui.theme.*
import com.tuapp.citas.util.fechaLargaDesdeIso
import java.time.LocalTime

@Composable
fun ConfirmarCitaScreen(
    medicoId: String,
    fecha: String,
    hora: String,
    alConfirmarExitoso: () -> Unit,
    alVolver: () -> Unit
) {
    val context = LocalContext.current
    val medico = remember(medicoId) { Repositorio.obtenerMedico(medicoId) }
    val especialidad = remember(medico) {
        medico?.let { Repositorio.obtenerEspecialidad(it.especialidadId) }
    }
    var motivo by remember { mutableStateOf("") }

    // Cada consulta dura 30 minutos: "09:30 a 10:00"
    val rangoHora = remember(hora) {
        try {
            "$hora a ${LocalTime.parse(hora).plusMinutes(30)}"
        } catch (e: Exception) {
            hora
        }
    }

    Scaffold(
        containerColor = Blanco,
        topBar = {
            BarraSuperiorConVolver(
                titulo = "Confirmar cita",
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
                        texto = "Agendar cita",
                        onClick = {
                            if (medico == null || especialidad == null) {
                                Toast.makeText(context, "No se encontró el médico seleccionado", Toast.LENGTH_SHORT).show()
                            } else {
                                val exito = Repositorio.agendarCita(
                                    medicoId = medico.id,
                                    especialidadId = especialidad.id,
                                    fecha = fecha,
                                    hora = hora,
                                    motivo = motivo.trim()
                                )
                                if (exito) {
                                    alConfirmarExitoso()
                                } else {
                                    Toast.makeText(context, "No se pudo agendar: el horario ya no está disponible o ya tienes una cita a esa hora", Toast.LENGTH_SHORT).show()
                                }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Tarjeta Médico
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
                        Text(it.cmp, fontSize = 13.sp, color = TextoGris)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Detalles de la reserva
            ItemDetalleCita(icono = Icons.Default.CalendarMonth, titulo = "Fecha", detalle = fechaLargaDesdeIso(fecha))
            HorizontalDivider(color = BordeGris.copy(alpha = 0.6f))
            ItemDetalleCita(icono = Icons.Default.Schedule, titulo = "Hora", detalle = rangoHora)
            HorizontalDivider(color = BordeGris.copy(alpha = 0.6f))
            ItemDetalleCita(icono = Icons.Default.LocationOn, titulo = "Tipo de atención", detalle = "Consulta presencial")
            HorizontalDivider(color = BordeGris.copy(alpha = 0.6f))
            ItemDetalleCita(icono = Icons.Default.Place, titulo = "Dirección", detalle = "Av. Los Olivos 123\nLima")

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("Motivo de consulta ", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = AzulOscuro)
                Text("(opcional)", fontSize = 14.sp, color = TextoGris)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, BordeGris, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                if (motivo.isEmpty()) {
                    Text("Consulta de rutina", fontSize = 14.sp, color = TextoOscuro)
                }
                BasicTextField(
                    value = motivo,
                    onValueChange = { motivo = it.take(200) },
                    textStyle = TextStyle(fontSize = 14.sp, color = TextoOscuro),
                    cursorBrush = SolidColor(AzulPrimario),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ItemDetalleCita(icono: ImageVector, titulo: String, detalle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(AzulClaro),
            contentAlignment = Alignment.Center
        ) {
            Icon(icono, contentDescription = null, tint = AzulPrimario, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(titulo, fontSize = 13.sp, color = TextoGris)
            Text(detalle, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = AzulOscuro)
        }
    }
}
