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

    for (p in lista) {
        println("Producto agregado: ${p.nombre}")
    }

    println("DETALLE DEL CARRITO")
    var sumSubtotal = 0.0
    var idx = 1
    for (p in lista) {
        val sub = calcularSubtotal(p.precio, p.cantidad)
        sumSubtotal += sub
        println(String.format("%d. %-20s x%d  S/%8.2f", idx, p.nombre, p.cantidad, sub))
        idx++
    }

    val igv = calcularIGV(sumSubtotal)
    val total = calcularTotal(sumSubtotal, igv)
    val cantTotal = lista.sumOf { it.cantidad }

    println(String.format("%-22s: %d", "Cantidad de productos", cantTotal))
    println(String.format("%-22s: S/ %8.2f", "Subtotal", sumSubtotal))
    println(String.format("%-22s: S/ %8.2f", "IGV (18%)", igv))
    println(String.format("%-22s: S/ %8.2f", "TOTAL A PAGAR", total))

    val masCaro = lista.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " + String.format("(S/%.2f)", masCaro.precio))
    }

    val descuento = when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }

    if (descuento > 0) {
        println("Descuento aplicado: 5% por compra mayor a S/ 3000")
        println(String.format("%-22s: S/ %8.2f", "TOTAL CON DESCUENTO", total - descuento))
    }

    println("Gracias por su compra, $cliente!")
}