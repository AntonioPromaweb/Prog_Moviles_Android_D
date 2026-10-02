package com.tuapp.navlab.navigation

sealed class Screen(val route: String) {
    object Store : Screen(route = "store")
    object Detail : Screen(route = "detail/{productoId}") {
        fun createRoute(productoId: Int): String = "detail/$productoId"
    }
}