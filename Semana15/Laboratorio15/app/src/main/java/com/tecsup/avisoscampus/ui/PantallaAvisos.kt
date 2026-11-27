package com.tecsup.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.model.PermisoUi
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.receiver.ModoAvionReceiver
import com.tecsup.avisoscampus.ui.theme.AmarilloAcento
import com.tecsup.avisoscampus.ui.theme.AzulEspacial
import com.tecsup.avisoscampus.ui.theme.CianNeon
import com.tecsup.avisoscampus.ui.theme.RojoPeligro
import com.tecsup.avisoscampus.ui.theme.TextoClaro
import com.tecsup.avisoscampus.ui.theme.TextoSecundarioEspacial
import com.tecsup.avisoscampus.ui.theme.VerdePermitido
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            if (!mostrado) vm.noSePudoMostrar()
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

    val avisos by vm.avisos.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()

    DisposableEffect(escuchando) {
        if (escuchando) {
            val receptor = ModoAvionReceiver { activo -> vm.alCambiarModoAvion(activo) }
            ContextCompat.registerReceiver(
                contexto,
                receptor,
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }

    val pedirPermiso = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            vm.permisoRespondido(true)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FondoEstrellas()
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                "AVISOSCAMPUS",
                                fontWeight = FontWeight.Black,
                                letterSpacing = 2.sp
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = AzulEspacial.copy(alpha = 0.9f),
                            titleContentColor = AmarilloAcento
                        )
                    )
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.5.dp)
                    ) {
                        drawRect(color = CianNeon)
                    }
                }
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
                mensaje?.let { texto ->
                    MensajeError(texto) {
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                        contexto.startActivity(intent)
                    }
                }
                TarjetaAvisos(vm)
                TarjetaRecordatorio(vm)
                TarjetaReceptor(vm)
                HistorialAvisos(
                    avisos = avisos,
                    onBorrar = { vm.borrarHistorial() }
                )
            }
        }
    }
}

@Composable
private fun TarjetaBase(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CutCornerShape(10.dp),
        border = BorderStroke(1.dp, CianNeon.copy(alpha = 0.4f)),
        colors = CardDefaults.cardColors(containerColor = AzulEspacial.copy(alpha = 0.9f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CianNeon
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
                Text("Estado:", color = TextoClaro)
                EtiquetaPermiso(permiso)
            }
            BotonAnimado(
                onClick = onPermitir,
                tipo = TipoBoton.RELLENO
            ) {
                Text("Permitir notificaciones")
            }
        }
    }
}

@Composable
private fun MensajeError(mensaje: String, onAbrirAjustes: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF3B1218),
            shape = CutCornerShape(8.dp),
            border = BorderStroke(1.dp, RojoPeligro)
        ) {
            Text(
                text = mensaje,
                color = RojoPeligro,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp)
            )
        }
        BotonAnimado(
            onClick = onAbrirAjustes,
            tipo = TipoBoton.CON_BORDE
        ) {
            Text("Abrir ajustes")
        }
    }
}

@Composable
private fun EtiquetaPermiso(permiso: PermisoUi) {
    val (texto, fondoObjetivo, colorObjetivo) = when (permiso) {
        PermisoUi.SinPedir -> Triple("Sin pedir", Color(0xFF1E2A3A), TextoSecundarioEspacial)
        PermisoUi.Concedido -> Triple("Permitido", Color(0xFF0F3D20), VerdePermitido)
        PermisoUi.Denegado -> Triple("Denegado", Color(0xFF4D1212), RojoPeligro)
    }

    val fondoAnimado by animateColorAsState(targetValue = fondoObjetivo, label = "FondoPermiso")
    val colorTextoAnimado by animateColorAsState(targetValue = colorObjetivo, label = "ColorTextoPermiso")

    Surface(color = fondoAnimado, shape = CutCornerShape(4.dp)) {
        Text(
            text = texto,
            color = colorTextoAnimado,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
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
            BotonAnimado(
                onClick = { vm.avisarAhora() },
                tipo = TipoBoton.RELLENO
            ) {
                Text("Mostrar aviso")
            }
        }
    }
}

@Composable
private fun TarjetaRecordatorio(vm: AvisosViewModel) {
    val faltan by vm.faltan.collectAsState()
    TarjetaBase("Recordatorio") {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BotonAnimado(
                    onClick = { vm.recordarEn() },
                    enabled = faltan == null,
                    tipo = TipoBoton.RELLENO
                ) {
                    Text("Recordar en 10 s")
                }
                BotonAnimado(
                    onClick = { vm.cancelarRecordatorio() },
                    enabled = faltan != null,
                    tipo = TipoBoton.CON_BORDE
                ) {
                    Text("Cancelar")
                }
            }
            faltan?.let { segundos ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Faltan ", fontWeight = FontWeight.Bold, color = TextoClaro)
                    AnimatedContent(
                        targetState = segundos,
                        transitionSpec = {
                            (slideInVertically { height -> -height } + fadeIn()) togetherWith
                                    (slideOutVertically { height -> height } + fadeOut())
                        },
                        label = "FaltanAnimacion"
                    ) { num ->
                        Text(
                            text = "$num",
                            fontWeight = FontWeight.Black,
                            color = AmarilloAcento,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Text(" s", fontWeight = FontWeight.Bold, color = TextoClaro)
                }
            }
        }
    }
}

@Composable
private fun TarjetaReceptor(vm: AvisosViewModel) {
    val escuchando by vm.escuchandoAvion.collectAsState()
    TarjetaBase("Receptor de anuncios") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Escuchar el modo avión", color = TextoClaro, modifier = Modifier.weight(1f))
            Switch(
                checked = escuchando,
                onCheckedChange = { vm.cambiarEscucha(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AmarilloAcento,
                    checkedTrackColor = CianNeon,
                    uncheckedThumbColor = TextoSecundarioEspacial,
                    uncheckedTrackColor = Color(0xFF1B263B)
                )
            )
        }
        Text(
            "Activa o desactiva el modo avión del celular",
            color = TextoSecundarioEspacial,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
