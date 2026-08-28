package com.example.laboratorio02

data class Producto(val nombre: String, val precio: Double, val cantidad: Int)

fun calcularSubtotal(precio: Double, cantidad: Int): Double = precio * cantidad
fun calcularIGV(subtotal: Double): Double = subtotal * 0.18
fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

fun main() {
    val cliente = "Luis Vasquez"
    val lista = listOf(
        Producto("Laptop HP", 2500.0, 1),
        Producto("Mouse Logitech", 45.5, 2),
        Producto("Audifonos Sony", 120.0, 1),
        Producto("USB Kingston 64GB", 25.0, 3)
    )

    println("==")
    println("CARRITO DE COMPRAS")
    println("TIENDA TECSUP")
    println("==")
    println("Cliente: $cliente")

    println("DETALLE DEL CARRITO")
    var sumSubtotal = 0.0
    var idx = 1
    for (p in lista) {
        val sub = calcularSubtotal(p.precio, p.cantidad)
        sumSubtotal += sub
        println(String.format("%d. %-20s x%d  S/%8.2f", idx, p.nombre, p.cantidad, sub))
        idx++
    }
}