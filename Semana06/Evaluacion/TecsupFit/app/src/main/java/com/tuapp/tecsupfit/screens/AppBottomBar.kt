package com.tuapp.tecsupfit.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tuapp.tecsupfit.navigation.Screen
import com.tuapp.tecsupfit.ui.theme.CardFondo
import com.tuapp.tecsupfit.ui.theme.VerdePrimary

@Composable
fun AppBottomBar(navController: NavController, currentRoute: String) {
    NavigationBar(
        containerColor = CardFondo
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == Screen.Home.route,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = VerdePrimary,
                selectedTextColor = VerdePrimary,
                indicatorColor = VerdePrimary.copy(alpha = 0.15f)
            ),
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Reservas") },
            label = { Text("Reservas") },
            selected = currentRoute == Screen.Reservations.route,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = VerdePrimary,
                selectedTextColor = VerdePrimary,
                indicatorColor = VerdePrimary.copy(alpha = 0.15f)
            ),
            onClick = {
                if (currentRoute != Screen.Reservations.route) {
                    navController.navigate(Screen.Reservations.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Rutinas") },
            label = { Text("Rutinas") },
            selected = currentRoute == Screen.Routines.route,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = VerdePrimary,
                selectedTextColor = VerdePrimary,
                indicatorColor = VerdePrimary.copy(alpha = 0.15f)
            ),
            onClick = {
                if (currentRoute != Screen.Routines.route) {
                    navController.navigate(Screen.Routines.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentRoute == Screen.Profile.route,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = VerdePrimary,
                selectedTextColor = VerdePrimary,
                indicatorColor = VerdePrimary.copy(alpha = 0.15f)
            ),
            onClick = {
                if (currentRoute != Screen.Profile.route) {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}
