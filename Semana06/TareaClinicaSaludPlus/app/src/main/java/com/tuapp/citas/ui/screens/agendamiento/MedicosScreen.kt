package com.tuapp.citas.ui.screens.agendamiento

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.data.model.Medico
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.BarraSuperiorConVolver
import com.tuapp.citas.ui.components.ChipDisponibilidad
import com.tuapp.citas.ui.components.FotoMedico
import com.tuapp.citas.ui.theme.AzulOscuro
import com.tuapp.citas.ui.theme.Blanco
import com.tuapp.citas.ui.theme.BordeGris
import com.tuapp.citas.ui.theme.EstrellaAmarilla
import com.tuapp.citas.ui.theme.FondoCampo
import com.tuapp.citas.ui.theme.TextoGris

@Composable
fun MedicosScreen(
    especialidadId: String,
    alSeleccionarMedico: (String) -> Unit,
    alVolver: () -> Unit
) {
    var buscando by remember { mutableStateOf(false) }
    var busqueda by remember { mutableStateOf("") }

    val especialidad = remember(especialidadId) {
        Repositorio.obtenerEspecialidad(especialidadId)
    }
    val medicos = remember(especialidadId, busqueda) {
        Repositorio.buscarMedicos(especialidadId, busqueda)
    }

    Scaffold(
        containerColor = Blanco,
        topBar = {
            BarraSuperiorConVolver(
                titulo = "Médicos de ${especialidad?.nombre ?: "Especialidad"}",
                alVolver = alVolver,
                acciones = {
                    IconButton(onClick = {
                        buscando = !buscando
                        if (!buscando) busqueda = ""
                    }) {
                        Icon(
                            imageVector = if (buscando) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Buscar médico",
                            tint = AzulOscuro
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Blanco)
                .padding(horizontal = 16.dp)
        ) {
            if (buscando) {
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    placeholder = { Text("Buscar médico...", color = TextoGris) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = FondoCampo,
                        unfocusedContainerColor = FondoCampo,
                        focusedBorderColor = FondoCampo,
                        unfocusedBorderColor = FondoCampo
                    ),
                    singleLine = true
                )
            }

            if (medicos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay médicos disponibles para esta búsqueda.",
                        color = TextoGris,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(medicos) { medico ->
                        TarjetaMedico(
                            medico = medico,
                            onAgendarClick = { alSeleccionarMedico(medico.id) }
                        )
                        HorizontalDivider(color = BordeGris.copy(alpha = 0.6f))
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaMedico(
    medico: Medico,
    onAgendarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAgendarClick() }
            .padding(vertical = 14.dp, horizontal = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        FotoMedico(fotoRes = medico.fotoRes, tamano = 64.dp)

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = medico.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Ver horarios",
                    tint = TextoGris
                )
            }
            Text(
                text = medico.cargo,
                fontSize = 14.sp,
                color = TextoGris
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Calificación",
                    tint = EstrellaAmarilla,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${medico.calificacion} (${medico.resenas})",
                    fontSize = 13.sp,
                    color = TextoGris
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                ChipDisponibilidad(texto = medico.disponibilidad)
            }
        }
    }
}
