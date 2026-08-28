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

    fun calcularDescuento(): Double {
        val total = calcularTotal()
        return when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
    }

    fun obtenerProductoMasCaro(): ProductoBase? {
        return productos.maxByOrNull { it.getPrecioBase() }
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

    carrito.agregarProducto(ProductoFisico("Laptop HP", 2500.0, 1))
    carrito.agregarProducto(ProductoFisico("Mouse Logitech", 45.5, 2))
    carrito.agregarProducto(ProductoFisico("Audifonos Sony", 120.0, 1))
    carrito.agregarProducto(ProductoFisico("USB Kingston 64GB", 25.0, 3))

    for (p in carrito.obtenerProductos()) {
        println("Producto agregado: ${p.getNombre()}")
    }

    println("DETALLE DEL CARRITO")
    var idx = 1
    for (p in carrito.obtenerProductos()) {
        if (p is ProductoFisico) {
            val sub = p.calcularSubtotal()
            println(String.format("%d. %-20s x%d  S/%8.2f", idx, p.getNombre(), p.getCantidad(), sub))
            idx++
        }
    }

    val subtotal = carrito.calcularSubtotalTotal()
    val igv = carrito.calcularIGV()
    val total = carrito.calcularTotal()
    val cantTotal = carrito.obtenerProductos().sumOf { (it as ProductoFisico).getCantidad() }

    println(String.format("%-22s: %d", "Cantidad de productos", cantTotal))
    println(String.format("%-22s: S/ %8.2f", "Subtotal", subtotal))
    println(String.format("%-22s: S/ %8.2f", "IGV (18%)", igv))
    println(String.format("%-22s: S/ %8.2f", "TOTAL A PAGAR", total))

    val masCaro = carrito.obtenerProductoMasCaro()
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.getNombre()} " + String.format("(S/%.2f)", masCaro.getPrecioBase()))
    }

    val descuento = carrito.calcularDescuento()
    val totalConDescuento = total - descuento

    if (descuento > 0) {
        println("Descuento aplicado: 5% por compra mayor a S/ 3000")
        println(String.format("%-22s: S/ %8.2f", "TOTAL CON DESCUENTO", totalConDescuento))
    }

    println("Gracias por su compra, $cliente!")
}