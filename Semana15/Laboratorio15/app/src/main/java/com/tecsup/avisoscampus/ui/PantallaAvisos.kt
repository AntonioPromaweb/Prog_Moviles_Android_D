package com.tecsup.avisoscampus.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.tecsup.avisoscampus.model.PermisoUi
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

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        vm.permisoRespondido(concedido)
    }

    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }

    val pedirPermiso = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            vm.permisoRespondido(true)
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
            TarjetaPermiso(vm, pedirPermiso)
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
private fun TarjetaPermiso(vm: AvisosViewModel, onPermitir: () -> Unit) {
    val permiso by vm.permiso.collectAsState()
    TarjetaBase("Permiso de notificaciones") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Estado:")
                EtiquetaPermiso(permiso)
            }
            Button(onClick = onPermitir) { Text("Permitir notificaciones") }
        }
        // TODO Paso 8: mensaje de error y botón "Abrir ajustes"
    }
}

@Composable
private fun EtiquetaPermiso(permiso: PermisoUi) {
    val (texto, fondo, color) = when (permiso) {
        PermisoUi.SinPedir -> Triple("Sin pedir", Color(0xFFE0E0E0), Color(0xFF616161))
        PermisoUi.Concedido -> Triple("Permitido", Color(0xFFD7F0DC), Color(0xFF1B5E20))
        PermisoUi.Denegado -> Triple("Denegado", Color(0xFFFBE0E0), Color(0xFFB71C1C))
    }
    Surface(color = fondo, shape = RoundedCornerShape(50)) {
        Text(
            text = texto,
            color = color,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun TarjetaAvisos(vm: AvisosViewModel) {
    TarjetaBase("Avisos") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { vm.avisarAhora() }) { Text("Mostrar aviso") }
        }
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
