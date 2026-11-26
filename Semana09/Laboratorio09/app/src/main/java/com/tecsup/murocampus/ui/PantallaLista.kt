package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.ui.theme.CianNeon
import com.tecsup.murocampus.ui.theme.FondoEspacial
import com.tecsup.murocampus.ui.theme.SuperficieOscura
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@Composable
fun PantallaLista(
    vm: PostsViewModel,
    onPostClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val filtro by vm.usuarioFiltro.collectAsStateWithLifecycle()
    val textoBusqueda by vm.busqueda.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { vm.buscar(it) },
            label = { Text("Buscar por título", fontFamily = FontFamily.Monospace) },
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
            trailingIcon = {
                if (textoBusqueda.isNotEmpty()) {
                    IconButton(onClick = { vm.buscar("") }) {
                        Text("✕", fontWeight = FontWeight.Bold, color = CianNeon)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 8.dp, end = 12.dp)
        )

        FilaChips(seleccionado = filtro, onElegir = { vm.filtrar(it) })

        Box(modifier = Modifier.fillMaxSize()) {
            when (val e = estado) {
                UiState.Cargando -> Cargando()
                is UiState.Error -> ErrorConReintento(
                    mensaje = e.mensaje,
                    onReintentar = { vm.cargar() }
                )
                is UiState.Exito -> if (e.datos.isEmpty()) {
                    Text(
                        text = "Sin publicaciones",
                        color = CianNeon,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(e.datos, key = { it.id }) { post ->
                            TarjetaPost(
                                userId = post.userId,
                                titulo = post.title,
                                cuerpo = post.body,
                                onClick = { onPostClick(post.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Fila desplazable: Todos, Usuario 1 ... Usuario 10 (el activo se ve relleno en cian)
@Composable
private fun FilaChips(seleccionado: Int?, onElegir: (Int?) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            val activo = seleccionado == null
            FilterChip(
                selected = activo,
                onClick = { onElegir(null) },
                label = {
                    Text(
                        text = "Todos",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal,
                        color = if (activo) FondoEspacial else CianNeon
                    )
                },
                shape = CutCornerShape(8.dp),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = activo,
                    borderColor = CianNeon.copy(alpha = 0.5f),
                    selectedBorderColor = CianNeon
                ),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = SuperficieOscura,
                    selectedContainerColor = CianNeon
                )
            )
        }
        items((1..10).toList()) { usuario ->
            val activo = seleccionado == usuario
            FilterChip(
                selected = activo,
                onClick = { onElegir(usuario) },
                label = {
                    Text(
                        text = "Usuario $usuario",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (activo) FontWeight.Bold else FontWeight.Normal,
                        color = if (activo) FondoEspacial else CianNeon
                    )
                },
                shape = CutCornerShape(8.dp),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = activo,
                    borderColor = CianNeon.copy(alpha = 0.5f),
                    selectedBorderColor = CianNeon
                ),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = SuperficieOscura,
                    selectedContainerColor = CianNeon
                )
            )
        }
    }
}
