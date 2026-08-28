package com.example.laboratorio02

abstract class ProductoBase(
    private val nombre: String,
    private val precioBase: Double
) {
    fun getNombre(): String = nombre
    fun getPrecioBase(): Double = precioBase

    abstract fun calcularSubtotal(): Double
}

class ProductoFisico(
    nombre: String,
    precioBase: Double,
    private var cantidad: Int
) : ProductoBase(nombre, precioBase) {

    fun getCantidad(): Int = cantidad

    override fun calcularSubtotal(): Double {
        return getPrecioBase() * cantidad
    }
}

class CarritoDeCompras {
    private val productos = mutableListOf<ProductoBase>()

    fun agregarProducto(producto: ProductoBase) {
        productos.add(producto)
    }

    fun obtenerProductos(): List<ProductoBase> = productos

    fun calcularSubtotalTotal(): Double {
        return productos.sumOf { it.calcularSubtotal() }
    }

    fun calcularIGV(): Double {
        return calcularSubtotalTotal() * 0.18
    }

    fun calcularTotal(): Double {
        return calcularSubtotalTotal() + calcularIGV()
    }
}

fun main() {
    val cliente = "Luis Vasquez"
    val carrito = CarritoDeCompras()

    println("==")
    println("CARRITO DE COMPRAS - POO")
    println("TIENDA TECSUP")
    println("==")
    println("Cliente: $cliente")
}