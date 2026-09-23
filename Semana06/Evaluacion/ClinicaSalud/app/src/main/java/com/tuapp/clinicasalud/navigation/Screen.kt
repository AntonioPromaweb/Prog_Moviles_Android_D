package com.tuapp.clinicasalud.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "home")

    object DoctorProfile : Screen(route = "doctorProfile/{doctorId}") {
        fun createRoute(doctorId: Int): String = "doctorProfile/$doctorId"
    }

    object Schedule : Screen(route = "schedule/{doctorId}") {
        fun createRoute(doctorId: Int): String = "schedule/$doctorId"
    }

    object Confirmation : Screen(route = "confirmation/{doctorId}/{date}/{time}") {
        fun createRoute(doctorId: Int, date: String, time: String): String =
            "confirmation/$doctorId/$date/$time"
    }

    object MyAppointments : Screen(route = "myAppointments")
    object MedicalHistory : Screen(route = "medicalHistory")
}
