package com.tecsup.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

private val VerdeExito = Color(0xFF2E7D32)

@Composable
fun PantallaCrear(vm: PostsViewModel, modifier: Modifier = Modifier) {
    val estado by vm.creado.collectAsStateWithLifecycle()
    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }

    // Exito: limpia el formulario. Error: conserva lo escrito para reintentar.
    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 12.dp)
        )
        Button(
            onClick = { vm.publicar(titulo.trim(), contenido.trim()) },
            enabled = titulo.isNotBlank() && contenido.isNotBlank() && estado !is UiState.Cargando,
            modifier = Modifier.padding(top = 16.dp)
        ) { Text(if (estado is UiState.Cargando) "Publicando…" else "Publicar") }

        when (val e = estado) {
            is UiState.Exito -> Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, VerdeExito),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text(
                    text = "Publicado con id ${e.datos.id}",
                    color = VerdeExito,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
            is UiState.Error -> Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text(
                    text = e.mensaje,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
            else -> Unit
        }
    }
}
