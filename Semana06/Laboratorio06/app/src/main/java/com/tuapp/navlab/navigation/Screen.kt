package com.tuapp.navlab.navigation

sealed class Screen(val route: String) {
    object Store : Screen(route = "store")
    object MisPedidos : Screen(route = "misPedidos")
    object Favoritos : Screen(route = "favoritos")
    object Perfil : Screen(route = "perfil")
    object Detail : Screen(route = "detail/{productoId}") {
        fun createRoute(productoId: Int): String = "detail/$productoId"
    }
}