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

fun main() {
    println("==")
    println("CARRITO DE COMPRAS - POO")
    println("TIENDA TECSUP")
    println("==")
}