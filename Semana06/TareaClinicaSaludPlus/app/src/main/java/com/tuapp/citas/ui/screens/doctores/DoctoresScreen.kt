package com.tuapp.citas.ui.screens.doctores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.tuapp.citas.ui.theme.TextoGris
import java.util.Locale

@Composable
fun DoctoresScreen(
    alVolver: () -> Unit
) {
    val medicosPorEsp = remember {
        Repositorio.todosLosMedicos().groupBy { it.especialidadId }
    }

    Scaffold(
        containerColor = Blanco,
        topBar = {
            BarraSuperiorConVolver(
                titulo = "Nuestros Doctores",
                alVolver = alVolver
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Blanco)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            medicosPorEsp.forEach { (espId, medicos) ->
                val especialidad = Repositorio.obtenerEspecialidad(espId)
                val nombreEspecialidad = especialidad?.nombre ?: "Especialidad"

                item(key = "header_$espId") {
                    Text(
                        text = nombreEspecialidad,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                }

                items(medicos, key = { it.id }) { medico ->
                    TarjetaDoctorItem(medico = medico)
                }
            }
        }
    }
}

@Composable
fun TarjetaDoctorItem(medico: Medico) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BordeGris)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            FotoMedico(fotoRes = medico.fotoRes, tamano = 64.dp)

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medico.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Text(
                    text = "${medico.cmp} • ${medico.cargo}",
                    fontSize = 13.sp,
                    color = TextoGris
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Calificación",
                        tint = EstrellaAmarilla,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${medico.calificacion} (${medico.resenas} reseñas)",
                        fontSize = 13.sp,
                        color = TextoGris
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "S/ ${String.format(Locale.getDefault(), "%.2f", medico.precioConsulta)}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro
                    )
                    ChipDisponibilidad(texto = medico.disponibilidad)
                }
            }
        }
    }
}
