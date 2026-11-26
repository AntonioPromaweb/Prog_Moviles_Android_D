package com.tecsup.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.murocampus.data.PostRepositoryRemote
import com.tecsup.murocampus.data.remote.RetrofitClient
import com.tecsup.murocampus.ui.BarraMuroCampus
import com.tecsup.murocampus.ui.FondoEstrellas
import com.tecsup.murocampus.ui.PantallaCrear
import com.tecsup.murocampus.ui.PantallaDetalle
import com.tecsup.murocampus.ui.PantallaLista
import com.tecsup.murocampus.ui.theme.AmarilloStar
import com.tecsup.murocampus.ui.theme.FondoEspacial
import com.tecsup.murocampus.ui.theme.MuroCampusTheme
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {

    private val vm: PostsViewModel by viewModels {
        PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Barras del sistema con iconos claros, porque el fondo es oscuro
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent {
            MuroCampusTheme {
                // null = se ve la lista; con un id = se ve el detalle
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }
                var creando by rememberSaveable { mutableStateOf(false) }

                BackHandler(enabled = idSeleccionado != null || creando) {
                    idSeleccionado = null
                    creando = false
                    vm.limpiarCreacion()
                    vm.limpiarBorrado()
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = FondoEspacial,
                    topBar = { BarraMuroCampus() },
                    floatingActionButton = {
                        if (idSeleccionado == null && !creando) {
                            FloatingActionButton(
                                onClick = { creando = true },
                                containerColor = AmarilloStar,
                                contentColor = FondoEspacial,
                                shape = CutCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "+",
                                    color = FondoEspacial,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        FondoEstrellas()
                        val id = idSeleccionado
                        val relleno = Modifier.padding(innerPadding)
                        when {
                            creando -> PantallaCrear(vm = vm, modifier = relleno)
                            id != null -> PantallaDetalle(
                                vm = vm,
                                id = id,
                                modifier = relleno,
                                onVolver = { idSeleccionado = null }
                            )
                            else -> PantallaLista(
                                vm = vm,
                                onPostClick = { idSeleccionado = it },
                                modifier = relleno
                            )
                        }
                    }
                }
            }
        }
    }
}
