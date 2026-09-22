package com.example.laboratorio04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.laboratorio04.ui.theme.Laboratorio04Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaCarrito(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaCarrito(modifier: Modifier = Modifier) {
    // Estados del formulario (Lab 03)
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    // Estado NUEVO (Lab 04): Lista observable de productos
    val productos = remember { mutableStateListOf<Producto>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi Carrito TECSUP",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}