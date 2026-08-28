package com.example.laboratorio02

data class Producto(val nombre: String, val precio: Double, val cantidad: Int)

fun calcularSubtotal(precio: Double, cantidad: Int): Double = precio * cantidad
fun calcularIGV(subtotal: Double): Double = subtotal * 0.18
fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

fun main() {
    println("==")
    println("CARRITO DE COMPRAS")
    println("TIENDA TECSUP")
    println("==")
}