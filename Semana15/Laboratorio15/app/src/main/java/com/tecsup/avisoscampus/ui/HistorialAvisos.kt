package com.tecsup.avisoscampus.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import com.tecsup.avisoscampus.ui.theme.AmarilloAcento
import com.tecsup.avisoscampus.ui.theme.AzulEspacial
import com.tecsup.avisoscampus.ui.theme.CianNeon
import com.tecsup.avisoscampus.ui.theme.NegroEspacial
import com.tecsup.avisoscampus.ui.theme.TextoClaro
import com.tecsup.avisoscampus.ui.theme.TextoSecundarioEspacial
import com.tecsup.avisoscampus.ui.theme.VioletaApoyo

@Composable
fun HistorialAvisos(
    avisos: List<Aviso>,
    onBorrar: () -> Unit
) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CutCornerShape(10.dp),
        border = BorderStroke(1.dp, CianNeon.copy(alpha = 0.4f)),
        colors = CardDefaults.cardColors(containerColor = AzulEspacial.copy(alpha = 0.9f))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Historial",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CianNeon
                )
                if (avisos.isNotEmpty()) {
                    BotonAnimado(
                        onClick = { mostrarConfirmacion = true },
                        tipo = TipoBoton.TEXTO
                    ) {
                        Text("Borrar historial")
                    }
                }
            }
            if (avisos.isEmpty()) {
                Text("Sin avisos todavía", color = TextoSecundarioEspacial)
            } else {
                avisos.take(20).forEach { aviso ->
                    key(aviso.id) {
                        val estado = remember {
                            MutableTransitionState(false).apply { targetState = true }
                        }
                        AnimatedVisibility(
                            visibleState = estado,
                            enter = fadeIn()
                        ) {
                            FilaAviso(aviso)
                        }
                    }
                }
            }
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            shape = CutCornerShape(12.dp),
            containerColor = AzulEspacial,
            title = { Text("¿Borrar todo el historial?", color = AmarilloAcento, fontWeight = FontWeight.Bold) },
            text = { Text("Esta acción eliminará de forma permanente todos los avisos registrados.", color = TextoClaro) },
            confirmButton = {
                BotonAnimado(
                    onClick = {
                        mostrarConfirmacion = false
                        onBorrar()
                    },
                    tipo = TipoBoton.RELLENO
                ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                BotonAnimado(
                    onClick = { mostrarConfirmacion = false },
                    tipo = TipoBoton.TEXTO
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun FilaAviso(aviso: Aviso) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF152038), CutCornerShape(6.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EtiquetaOrigen(aviso.origen)
        Column(modifier = Modifier.weight(1f)) {
            Text(aviso.titulo, fontWeight = FontWeight.Bold, color = TextoClaro)
            Text(aviso.texto, style = MaterialTheme.typography.bodySmall, color = TextoSecundarioEspacial)
        }
        Text(aviso.hora, style = MaterialTheme.typography.labelSmall, color = TextoSecundarioEspacial)
    }
}

@Composable
private fun EtiquetaOrigen(origen: Origen) {
    val (fondo, textoColor) = when (origen) {
        Origen.BOTON -> Pair(CianNeon, NegroEspacial)
        Origen.RECORDATORIO -> Pair(AmarilloAcento, NegroEspacial)
        Origen.MODO_AVION -> Pair(VioletaApoyo, Color.White)
    }
    Surface(color = fondo, shape = CutCornerShape(4.dp)) {
        Text(
            text = origen.texto,
            color = textoColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}
