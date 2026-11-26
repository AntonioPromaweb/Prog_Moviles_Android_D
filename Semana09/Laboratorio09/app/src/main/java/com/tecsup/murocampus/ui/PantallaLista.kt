package com.tecsup.murocampus.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    Column(modifier = modifier.fillMaxSize()) {
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

// Fila desplazable: Todos, Usuario 1 ... Usuario 10 (el activo se ve relleno)
@Composable
private fun FilaChips(seleccionado: Int?, onElegir: (Int?) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = seleccionado == null,
                onClick = { onElegir(null) },
                label = { Text("Todos") }
            )
        }
        items((1..10).toList()) { usuario ->
            FilterChip(
                selected = seleccionado == usuario,
                onClick = { onElegir(usuario) },
                label = { Text("Usuario $usuario") }
            )
        }
    }
}
