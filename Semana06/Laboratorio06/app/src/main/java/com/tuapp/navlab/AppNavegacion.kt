package com.tuapp.navlab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var currentRoute by remember { mutableStateOf(DestinoDrawer.INICIO) }

    val productos = remember {
        listOf(
            Producto(1, "Audífonos Inalámbricos", "S/ 89.00", "Audífonos Bluetooth de alta calidad"),
            Producto(2, "Smartwatch Deportivo", "S/ 199.00", "Reloj inteligente con sensor de ritmo cardiaco"),
            Producto(3, "Funda de Celular Ultra Slim", "S/ 25.00", "Protección contra caídas e impactos")
        )
    }

    if (!AuthManager.isLoggedIn) {
        LoginScreen(
            onLoginSuccess = {
                AuthManager.isLoggedIn = true
            }
        )
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawerContent(
                    currentRoute = currentRoute,
                    onNavigateTo = { destino -> currentRoute = destino },
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF5E2E8C))
                            .statusBarsPadding()
                            .padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Column(
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Text(
                                text = "TECSUP Store",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentRoute.titulo,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 13.sp
                            )
                        }
                    }
                },
                containerColor = Color.White
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentRoute) {
                        DestinoDrawer.INICIO -> {
                            InicioScreen(
                                productos = productos,
                                onComprarClick = { producto ->
                                    val pedido = PedidosManager.agregarPedido(producto)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("¡Pedido ${pedido.codigo} registrado con éxito!")
                                    }
                                },
                                onReportarClick = { producto ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar("El producto '${producto.nombre}' ha sido reportado.")
                                    }
                                }
                            )
                        }

                        DestinoDrawer.FAVORITOS -> {
                            FavoritosScreen(
                                onComprarClick = { producto ->
                                    val pedido = PedidosManager.agregarPedido(producto)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("¡Pedido ${pedido.codigo} registrado con éxito!")
                                    }
                                },
                                onReportarClick = { producto ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar("El producto '${producto.nombre}' ha sido reportado.")
                                    }
                                }
                            )
                        }

                        DestinoDrawer.MIS_PEDIDOS -> {
                            MisPedidosScreen()
                        }

                        DestinoDrawer.PERFIL -> {
                            PerfilScreen(
                                onLogout = {
                                    AuthManager.logout()
                                }
                            )
                        }

                        DestinoDrawer.CERRAR_SESION -> {
                            AuthManager.logout()
                        }
                    }
                }
            }
        }
    }
}
