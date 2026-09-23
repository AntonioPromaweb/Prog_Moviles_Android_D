package com.tuapp.tecsupfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.tecsupfit.screens.ClassDetailScreen
import com.tuapp.tecsupfit.screens.ConfirmationScreen
import com.tuapp.tecsupfit.screens.HomeScreen
import com.tuapp.tecsupfit.screens.ProfileScreen
import com.tuapp.tecsupfit.screens.ReservationsScreen
import com.tuapp.tecsupfit.screens.RoutinesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.ClassDetail.route,
            arguments = listOf(navArgument(name = "classId") { type = NavType.IntType })
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getInt("classId") ?: 0
            ClassDetailScreen(navController, classId)
        }
        composable(
            route = Screen.Confirmation.route,
            arguments = listOf(navArgument(name = "classId") { type = NavType.IntType })
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getInt("classId") ?: 0
            ConfirmationScreen(navController, classId)
        }
        composable(Screen.Reservations.route) {
            ReservationsScreen(navController)
        }
        composable(Screen.Routines.route) {
            RoutinesScreen(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}