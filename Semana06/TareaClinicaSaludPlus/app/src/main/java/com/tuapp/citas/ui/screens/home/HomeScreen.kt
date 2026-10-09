package com.tuapp.citas.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.tuapp.citas.util.fechaLargaDesdeIso
import com.tuapp.citas.R
import com.tuapp.citas.data.model.Especialidad
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.iconoDestacada
import com.tuapp.citas.ui.theme.*

@Composable
fun HomeScreen(
    alIrAAgendar: () -> Unit,
    alIrAMisCitas: () -> Unit,
    alIrAPerfil: () -> Unit
) {
    var destinoSeleccionado by remember { mutableStateOf(0) }
    val usuario = Repositorio.usuarioActual
    val destacadas = remember { Repositorio.especialidadesDestacadas() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var mostrarNotificaciones by remember { mutableStateOf(false) }
    var citasNotificacion by remember { mutableStateOf(Repositorio.citasDelUsuario()) }

    val coloresItem = NavigationBarItemDefaults.colors(
        selectedIconColor = AzulPrimario,
        selectedTextColor = AzulPrimario,
        unselectedIconColor = TextoGris,
        unselectedTextColor = TextoGris,
        indicatorColor = Blanco
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Blanco) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AzulClaro)
                        .padding(horizontal = 20.dp, vertical = 28.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo),
                        contentDescription = "Logo SaludPlus",
                        modifier = Modifier.size(width = 56.dp, height = 52.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = usuario?.nombreCompleto ?: "Paciente",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro
                    )
                    Text(
                        text = usuario?.correo?.ifBlank { null } ?: usuario?.telefono ?: "",
                        fontSize = 13.sp,
                        color = TextoGris
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                val itemsMenu = listOf(
                    Triple("Inicio", Icons.Default.Home, { }),
                    Triple("Agendar cita", Icons.Default.AddCircleOutline, alIrAAgendar),
                    Triple("Mis citas", Icons.Default.CalendarMonth, alIrAMisCitas),
                    Triple("Mis datos", Icons.Default.Person, alIrAPerfil)
                )
                itemsMenu.forEachIndexed { indice, (titulo, icono, accion) ->
                    NavigationDrawerItem(
                        label = { Text(titulo) },
                        icon = { Icon(icono, contentDescription = null) },
                        selected = indice == 0,
                        onClick = {
                            scope.launch { drawerState.close() }
                            accion()
                        },
                        modifier = Modifier.padding(horizontal = 12.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = AzulClaro,
                            selectedIconColor = AzulPrimario,
                            selectedTextColor = AzulPrimario
                        )
                    )
                }
            }
        }
    ) {
    Scaffold(
        containerColor = Blanco,
        bottomBar = {
            NavigationBar(
                containerColor = Blanco,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = destinoSeleccionado == 0,
                    onClick = { destinoSeleccionado = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio", fontSize = 11.sp) },
                    colors = coloresItem
                )
                NavigationBarItem(
                    selected = destinoSeleccionado == 1,
                    onClick = {
                        destinoSeleccionado = 1
                        alIrAMisCitas()
                    },
                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Citas") },
                    label = { Text("Citas", fontSize = 11.sp) },
                    colors = coloresItem
                )
                NavigationBarItem(
                    selected = destinoSeleccionado == 2,
                    onClick = { destinoSeleccionado = 2 },
                    icon = { Icon(Icons.Default.Description, contentDescription = "Resultados") },
                    label = { Text("Resultados", fontSize = 11.sp) },
                    colors = coloresItem
                )
                NavigationBarItem(
                    selected = destinoSeleccionado == 3,
                    onClick = {
                        destinoSeleccionado = 3
                        alIrAPerfil()
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil", fontSize = 11.sp) },
                    colors = coloresItem
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Blanco)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            // Menú y notificaciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = AzulOscuro,
                        modifier = Modifier.size(26.dp)
                    )
                }
                IconButton(onClick = {
                    citasNotificacion = Repositorio.citasDelUsuario()
                    mostrarNotificaciones = true
                }) {
                    BadgedBox(badge = {
                        if (Repositorio.citasDelUsuario().isNotEmpty()) Badge()
                    }) {
                        Icon(
                            imageVector = Icons.Default.NotificationsNone,
                            contentDescription = "Notificaciones",
                            tint = AzulOscuro,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Text(
                text = "¡Hola, ${usuario?.nombreCompleto?.split(" ")?.firstOrNull() ?: "Paciente"}!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = AzulOscuro
            )
            Text(
                text = "¿Qué deseas hacer hoy?",
                fontSize = 17.sp,
                color = TextoGris
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TarjetaAccion(
                    titulo = "Agendar cita",
                    iconoRes = R.drawable.ic_accion_agendar,
                    colorFondo = Color(0xFFDDEBFD),
                    colorTexto = Color(0xFF1D63E0),
                    modifier = Modifier.weight(1f),
                    onClick = alIrAAgendar
                )
                TarjetaAccion(
                    titulo = "Mis citas",
                    iconoRes = R.drawable.ic_accion_citas,
                    colorFondo = Color(0xFFD9F7E7),
                    colorTexto = Color(0xFF1FA055),
                    modifier = Modifier.weight(1f),
                    onClick = alIrAMisCitas
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TarjetaAccion(
                    titulo = "Mis datos",
                    iconoRes = R.drawable.ic_accion_datos,
                    colorFondo = Color(0xFFEBE0FC),
                    colorTexto = Color(0xFF8B3FE0),
                    modifier = Modifier.weight(1f),
                    onClick = alIrAPerfil
                )
                TarjetaAccion(
                    titulo = "Resultados",
                    iconoRes = R.drawable.ic_accion_resultados,
                    colorFondo = Color(0xFFFFEDD8),
                    colorTexto = Color(0xFFF08A1C),
                    modifier = Modifier.weight(1f),
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sección Especialidades Destacadas con LazyRow
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Especialidades destacadas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Text(
                    text = "Ver todas",
                    color = AzulPrimario,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { alIrAAgendar() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(destacadas) { esp ->
                    ItemEspecialidadDestacada(
                        especialidad = esp,
                        onClick = alIrAAgendar
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
    }

    if (mostrarNotificaciones) {
        AlertDialog(
            onDismissRequest = { mostrarNotificaciones = false },
            title = { Text("Notificaciones", fontWeight = FontWeight.Bold, color = AzulOscuro) },
            text = {
                if (citasNotificacion.isEmpty()) {
                    Text("No tienes notificaciones por ahora.", color = TextoGris)
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        citasNotificacion.take(5).forEach { cita ->
                            val medico = Repositorio.obtenerMedico(cita.medicoId)
                            Text(
                                text = "Recordatorio: cita con ${medico?.nombre ?: "tu médico"} el ${fechaLargaDesdeIso(cita.fecha)} a las ${cita.hora}.",
                                fontSize = 14.sp,
                                color = AzulOscuro
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarNotificaciones = false }) { Text("Cerrar") }
            },
            containerColor = Blanco
        )
    }
}

@Composable
fun TarjetaAccion(
    titulo: String,
    iconoRes: Int,
    colorFondo: Color,
    colorTexto: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .height(122.dp)
            .background(colorFondo, RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconoRes),
            contentDescription = titulo,
            modifier = Modifier.size(46.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = titulo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colorTexto
        )
    }
}

@Composable
fun ItemEspecialidadDestacada(
    especialidad: Especialidad,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(96.dp)
            .height(124.dp)
            .background(Blanco, RoundedCornerShape(14.dp))
            .border(BorderStroke(1.dp, BordeGris), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = iconoDestacada(especialidad.iconoNombre)),
            contentDescription = especialidad.nombre,
            modifier = Modifier.size(54.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = especialidad.nombre,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = AzulOscuro,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 16.sp
        )
    }
}
