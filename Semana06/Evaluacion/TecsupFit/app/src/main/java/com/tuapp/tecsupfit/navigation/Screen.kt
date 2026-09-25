package com.tuapp.tecsupfit.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "home")

    object ClassDetail : Screen(route = "classDetail/{classId}") {
        fun createRoute(classId: Int): String = "classDetail/$classId"
    }

    object Confirmation : Screen(route = "confirmation/{classId}") {
        fun createRoute(classId: Int): String = "confirmation/$classId"
    }

    object Reservations : Screen(route = "reservations")
    object Routines : Screen(route = "routines")
    object Profile : Screen(route = "profile")
}
