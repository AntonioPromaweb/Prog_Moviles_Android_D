package com.tuapp.citas.ui.screens.sedes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.data.model.Sede
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.BarraSuperiorConVolver
import com.tuapp.citas.ui.theme.AzulClaro
import com.tuapp.citas.ui.theme.AzulOscuro
import com.tuapp.citas.ui.theme.AzulPrimario
import com.tuapp.citas.ui.theme.Blanco
import com.tuapp.citas.ui.theme.BordeGris
import com.tuapp.citas.ui.theme.TextoGris

@Composable
fun SedesScreen(
    alSeleccionarSede: (String) -> Unit,
    alVolver: () -> Unit
) {
    val sedes = remember { Repositorio.obtenerSedes() }

    Scaffold(
        containerColor = Blanco,
        topBar = {
            BarraSuperiorConVolver(
                titulo = "Sedes de la Clínica",
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sedes) { sede ->
                TarjetaSede(
                    sede = sede,
                    onClick = { alSeleccionarSede(sede.id) }
                )
            }
        }
    }
}

@Composable
fun TarjetaSede(
    sede: Sede,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BordeGris)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(AzulClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = AzulPrimario,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sede.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = sede.direccion,
                    fontSize = 13.sp,
                    color = TextoGris
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = TextoGris,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = sede.telefono,
                        fontSize = 13.sp,
                        color = TextoGris
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Seleccionar sede",
                tint = TextoGris
            )
        }
    }
}
