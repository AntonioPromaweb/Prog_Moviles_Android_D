package com.tecsup.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.murocampus.ui.theme.AmarilloStar
import com.tecsup.murocampus.ui.theme.CianNeon
import com.tecsup.murocampus.ui.theme.RojoSable
import com.tecsup.murocampus.ui.theme.SuperficieOscura
import com.tecsup.murocampus.viewmodel.DetalleUi
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(
    vm: PostsViewModel,
    id: Int,
    modifier: Modifier = Modifier,
    onVolver: () -> Unit = {}
) {
    val estado by vm.detalle.collectAsStateWithLifecycle()
    val estadoBorrado by vm.borrado.collectAsStateWithLifecycle()
    var mostrarDialogo by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(id) { vm.abrir(id) }

    LaunchedEffect(estadoBorrado) {
        if (estadoBorrado is UiState.Exito) {
            vm.limpiarBorrado()
            vm.cargar()
            onVolver()
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            containerColor = SuperficieOscura,
            shape = CutCornerShape(12.dp),
            title = {
                Text(
                    text = "Eliminar publicación",
                    color = RojoSable,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "¿Estás seguro de que deseas eliminar esta publicación?",
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogo = false
                        vm.borrar(id)
                    },
                    shape = CutCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RojoSable,
                        contentColor = Color.White
                    )
                ) {
                    Text("Eliminar", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar", color = CianNeon, fontFamily = FontFamily.Monospace)
                }
            }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val e = estado) {
            UiState.Cargando -> Cargando("Cargando publicación…")
            is UiState.Error -> ErrorConReintento(
                mensaje = e.mensaje,
                onReintentar = { vm.abrir(id) }
            )
            is UiState.Exito -> ContenidoDetalle(
                datos = e.datos,
                estadoBorrado = estadoBorrado,
                onBorrar = { mostrarDialogo = true }
            )
        }
    }
}

@Composable
private fun ContenidoDetalle(
    datos: DetalleUi,
    estadoBorrado: UiState<Unit>?,
    onBorrar: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        item {
            Card(
                shape = CutCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SuperficieOscura.copy(alpha = 0.85f)),
                border = BorderStroke(1.dp, CianNeon.copy(alpha = 0.8f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = datos.post.title,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.5.sp,
                        color = Color.White
                    )
                    Text(
                        text = datos.post.body,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                    Button(
                        onClick = onBorrar,
                        enabled = estadoBorrado !is UiState.Cargando,
                        shape = CutCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RojoSable,
                            contentColor = Color.White,
                            disabledContainerColor = RojoSable.copy(alpha = 0.4f)
                        ),
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text(
                            text = if (estadoBorrado is UiState.Cargando) "Eliminando…" else "Eliminar",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        if (estadoBorrado is UiState.Error) {
            item {
                Surface(
                    color = Color(0xFF33080C),
                    shape = CutCornerShape(8.dp),
                    border = BorderStroke(1.dp, RojoSable),
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
                ) {
                    Text(
                        text = estadoBorrado.mensaje,
                        color = RojoSable,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
        item {
            Text(
                text = "Comentarios (${datos.comentarios.size})",
                color = AmarilloStar,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }
        items(datos.comentarios, key = { it.id }) { c ->
            Surface(
                color = SuperficieOscura.copy(alpha = 0.7f),
                shape = CutCornerShape(8.dp),
                border = BorderStroke(1.dp, CianNeon.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = c.email,
                        color = CianNeon,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = c.body,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
