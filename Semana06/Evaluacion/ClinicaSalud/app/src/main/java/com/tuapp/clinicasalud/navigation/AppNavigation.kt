package com.tuapp.clinicasalud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.clinicasalud.screens.ConfirmationScreen
import com.tuapp.clinicasalud.screens.DoctorProfileScreen
import com.tuapp.clinicasalud.screens.HomeScreen
import com.tuapp.clinicasalud.screens.MedicalHistoryScreen
import com.tuapp.clinicasalud.screens.MyAppointmentsScreen
import com.tuapp.clinicasalud.screens.ProfileScreen
import com.tuapp.clinicasalud.screens.ScheduleScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.DoctorProfile.route,
            arguments = listOf(
                navArgument("doctorId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
            DoctorProfileScreen(navController = navController, doctorId = doctorId)
        }

        composable(
            route = Screen.Schedule.route,
            arguments = listOf(
                navArgument("doctorId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
            ScheduleScreen(navController = navController, doctorId = doctorId)
        }

        composable(
            route = Screen.Confirmation.route,
            arguments = listOf(
                navArgument("doctorId") { type = NavType.IntType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""
            ConfirmationScreen(
                navController = navController,
                doctorId = doctorId,
                date = date,
                time = time
            )
        }

        composable(Screen.MyAppointments.route) {
            MyAppointmentsScreen(navController = navController)
        }

        composable(Screen.MedicalHistory.route) {
            MedicalHistoryScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
