package com.tuapp.navlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuapp.navlab.screens.PantallaTemporal
import com.tuapp.navlab.screens.StoreScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Store.route
    ) {
        composable(Screen.Store.route) {
            StoreScreen(navController)
        }
        composable(Screen.MisPedidos.route) {
            PantallaTemporal("Mis pedidos")
        }
        composable(Screen.Favoritos.route) {
            PantallaTemporal("Favoritos")
        }
        composable(Screen.Perfil.route) {
            PantallaTemporal("Perfil")
        }
    }
}