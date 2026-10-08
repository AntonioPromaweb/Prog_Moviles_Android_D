package com.tuapp.navlab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class DestinoDrawer(val titulo: String, val icon: ImageVector) {
    INICIO("Inicio", Icons.Default.Home),
    MIS_PEDIDOS("Mis pedidos", Icons.Default.ShoppingBag),
    FAVORITOS("Favoritos", Icons.Default.Favorite),
    PERFIL("Perfil", Icons.Default.Person),
    CERRAR_SESION("Cerrar sesion", Icons.Default.ExitToApp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerContent(
    currentRoute: DestinoDrawer,
    onNavigateTo: (DestinoDrawer) -> Unit,
    closeDrawer: () -> Unit
) {
    val purpleBrand = Color(0xFF5E2E8C)
    val lightPurpleActive = Color(0xFFF3EAFB)

    ModalDrawerSheet(
        modifier = Modifier.width(310.dp),
        drawerContainerColor = Color.White
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE9DCF8)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LV",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = purpleBrand
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = AuthManager.usuarioNombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color(0xFF1E1E24)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = AuthManager.usuarioCorreo,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            color = Color(0xFFF2F2F2),
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        DestinoDrawer.values().forEach { destino ->
            val isSelected = destino == currentRoute

            val badgeCount = when (destino) {
                DestinoDrawer.FAVORITOS -> FavoritosManager.favoritos.size
                DestinoDrawer.MIS_PEDIDOS -> PedidosManager.pedidos.size
                else -> 0
            }

            NavigationDrawerItem(
                label = {
                    Text(
                        text = destino.titulo,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) purpleBrand else Color(0xFF333333),
                        fontSize = 15.sp
                    )
                },
                selected = isSelected,
                onClick = {
                    if (destino == DestinoDrawer.CERRAR_SESION) {
                        closeDrawer()
                        AuthManager.logout()
                    } else {
                        onNavigateTo(destino)
                        closeDrawer()
                    }
                },
                icon = {
                    if (badgeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = purpleBrand,
                                    contentColor = Color.White
                                ) {
                                    Text("$badgeCount")
                                }
                            }
                        ) {
                            Icon(
                                imageVector = destino.icon,
                                contentDescription = destino.titulo,
                                tint = if (isSelected) purpleBrand else Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = destino.icon,
                            contentDescription = destino.titulo,
                            tint = if (isSelected) purpleBrand else Color.Gray,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = lightPurpleActive,
                    unselectedContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 4.dp)
                    .height(52.dp)
            )
        }
    }
}
