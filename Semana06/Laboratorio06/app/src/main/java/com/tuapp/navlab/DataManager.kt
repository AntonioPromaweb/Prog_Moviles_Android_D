package com.tuapp.navlab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: String,
    val descripcion: String = ""
)

data class Pedido(
    val codigo: String,
    val productoNombre: String,
    val total: String,
    val estado: String
)

object FavoritosManager {
    val favoritos = mutableStateListOf<Producto>()

    fun toggleFavorito(producto: Producto) {
        if (esFavorito(producto)) {
            favoritos.removeAll { it.id == producto.id }
        } else {
            favoritos.add(producto)
        }
    }

    fun esFavorito(producto: Producto): Boolean {
        return favoritos.any { it.id == producto.id }
    }
}

object PedidosManager {
    val pedidos = mutableStateListOf<Pedido>(
        Pedido("ORD-1001", "Smartwatch Deportivo", "S/ 199.00", "Entregado")
    )

    private var contadorOrdenes = 1002

    fun agregarPedido(producto: Producto): Pedido {
        val nuevoPedido = Pedido(
            codigo = "ORD-$contadorOrdenes",
            productoNombre = producto.nombre,
            total = producto.precio,
            estado = "En preparación"
        )
        contadorOrdenes++
        pedidos.add(0, nuevoPedido)
        return nuevoPedido
    }
}

object AuthManager {
    var isLoggedIn by mutableStateOf(true)
    var usuarioNombre by mutableStateOf("Luis Vasquez")
    var usuarioCorreo by mutableStateOf("luis.vasquez.f@tecsup.edu.pe")

    fun login(correo: String, pass: String): Boolean {
        return if (correo.trim().equals("luis.vasquez.f@tecsup.edu.pe", ignoreCase = true) && pass.isNotBlank()) {
            isLoggedIn = true
            true
        } else {
            false
        }
    }

    fun logout() {
        isLoggedIn = false
    }
}
