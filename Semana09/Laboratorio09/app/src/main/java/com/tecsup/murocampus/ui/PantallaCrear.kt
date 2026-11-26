package com.tecsup.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.ui.theme.CianNeon
import com.tecsup.murocampus.ui.theme.FondoEspacial
import com.tecsup.murocampus.ui.theme.RojoSable
import com.tecsup.murocampus.ui.theme.VerdeNeon
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

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
            label = { Text("Título", fontFamily = FontFamily.Monospace) },
            singleLine = true,
            shape = CutCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CianNeon,
                unfocusedBorderColor = CianNeon.copy(alpha = 0.5f),
                focusedLabelColor = CianNeon,
                unfocusedLabelColor = Color(0xFFB0BEC5),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = CianNeon
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido", fontFamily = FontFamily.Monospace) },
            shape = CutCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CianNeon,
                unfocusedBorderColor = CianNeon.copy(alpha = 0.5f),
                focusedLabelColor = CianNeon,
                unfocusedLabelColor = Color(0xFFB0BEC5),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = CianNeon
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 12.dp)
        )
        Button(
            onClick = { vm.publicar(titulo.trim(), contenido.trim()) },
            enabled = titulo.isNotBlank() && contenido.isNotBlank() && estado !is UiState.Cargando,
            shape = CutCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CianNeon,
                contentColor = FondoEspacial,
                disabledContainerColor = CianNeon.copy(alpha = 0.3f)
            ),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = if (estado is UiState.Cargando) "Publicando…" else "Publicar",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        when (val e = estado) {
            is UiState.Exito -> Surface(
                color = Color(0xFF052218),
                shape = CutCornerShape(8.dp),
                border = BorderStroke(1.dp, VerdeNeon),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text(
                    text = "Publicado con id ${e.datos.id}",
                    color = VerdeNeon,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
            is UiState.Error -> Surface(
                color = Color(0xFF33080C),
                shape = CutCornerShape(8.dp),
                border = BorderStroke(1.dp, RojoSable),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text(
                    text = e.mensaje,
                    color = RojoSable,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
            else -> Unit
        }
    }
}
