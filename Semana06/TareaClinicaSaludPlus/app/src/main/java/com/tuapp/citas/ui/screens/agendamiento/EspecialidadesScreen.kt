package com.tuapp.citas.ui.screens.agendamiento

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.data.model.Especialidad
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.BarraSuperiorConVolver
import com.tuapp.citas.ui.components.iconoEspecialidad
import com.tuapp.citas.ui.theme.AzulOscuro
import com.tuapp.citas.ui.theme.Blanco
import com.tuapp.citas.ui.theme.BordeGris
import com.tuapp.citas.ui.theme.FondoCampo
import com.tuapp.citas.ui.theme.TextoGris

@Composable
fun EspecialidadesScreen(
    sedeId: String = "",
    alSeleccionarEspecialidad: (String) -> Unit,
    alVolver: () -> Unit
) {
    val sede = remember(sedeId) { Repositorio.obtenerSede(sedeId) }
    var busqueda by remember { mutableStateOf("") }
    val especialidadesFiltradas = remember(busqueda) {
        Repositorio.buscarEspecialidades(busqueda)
    }

    Scaffold(
        containerColor = Blanco,
        topBar = {
            BarraSuperiorConVolver(
                titulo = if (sede != null) "Especialidades en ${sede.nombre}" else "Especialidades",
                alVolver = alVolver
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
            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar especialidad...", color = TextoGris) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = TextoGris
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = FondoCampo,
                    unfocusedContainerColor = FondoCampo,
                    focusedBorderColor = FondoCampo,
                    unfocusedBorderColor = FondoCampo
                ),
                singleLine = true
            )

            // Lista con LazyColumn
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(especialidadesFiltradas) { esp ->
                    TarjetaEspecialidad(
                        especialidad = esp,
                        onClick = { alSeleccionarEspecialidad(esp.id) }
                    )
                    HorizontalDivider(color = BordeGris.copy(alpha = 0.6f))
                }
            }
        }
    }
}

@Composable
fun TarjetaEspecialidad(
    especialidad: Especialidad,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconoEspecialidad(especialidad.iconoNombre)),
            contentDescription = especialidad.nombre,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = especialidad.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AzulOscuro
            )
            Text(
                text = especialidad.descripcion,
                fontSize = 13.sp,
                color = TextoGris
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Ver médicos",
            tint = TextoGris
        )
    }
}
