package com.tecsup.avisoscampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    // Dentro de PantallaAvisos (ya viene hecho):
    val contexto = LocalContext.current
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            // TODO Paso 8: si no se mostró, avisar al ViewModel
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaPermiso(vm)
            TarjetaAvisos(vm)
            TarjetaRecordatorio(vm)
            TarjetaReceptor(vm)
            // TODO Paso 7: tarjeta Historial (HistorialAvisos)
        }
    }
}

@Composable
private fun TarjetaBase(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            contenido()
        }
    }
}

@Composable
private fun TarjetaPermiso(vm: AvisosViewModel) {
    TarjetaBase("Permiso de notificaciones") {
        // TODO Paso 2: etiqueta (Sin pedir / Permitido / Denegado) y botón "Permitir notificaciones"
        // TODO Paso 8: mensaje de error y botón "Abrir ajustes"
    }
}

@Composable
private fun TarjetaAvisos(vm: AvisosViewModel) {
    TarjetaBase("Avisos") {
        // TODO Paso 3: botón "Mostrar aviso" -> vm.avisarAhora()
    }
}

@Composable
private fun TarjetaRecordatorio(vm: AvisosViewModel) {
    TarjetaBase("Recordatorio") {
        // TODO Paso 5: botones "Recordar en 10 s" y "Cancelar", y texto "Faltan N s"
    }
}

@Composable
private fun TarjetaReceptor(vm: AvisosViewModel) {
    TarjetaBase("Receptor de anuncios") {
        // TODO Paso 6: Switch "Escuchar el modo avión"
    }
}
