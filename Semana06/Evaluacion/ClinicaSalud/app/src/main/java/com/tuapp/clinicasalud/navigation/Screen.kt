package com.tuapp.clinicasalud.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DoctorProfile : Screen("doctorProfile/{doctorId}") {
        fun createRoute(doctorId: Int): String = "doctorProfile/$doctorId"
    }
    object Schedule : Screen("schedule/{doctorId}") {
        fun createRoute(doctorId: Int): String = "schedule/$doctorId"
    }
    object Confirmation : Screen("confirmation/{doctorId}/{date}/{time}") {
        fun createRoute(doctorId: Int, date: String, time: String): String =
            "confirmation/$doctorId/$date/$time"
    }
    object MyAppointments : Screen("myAppointments")
    object MedicalHistory : Screen("medicalHistory")
    object Profile : Screen("profile")
}
