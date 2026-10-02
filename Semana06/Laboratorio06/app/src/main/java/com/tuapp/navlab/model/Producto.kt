package com.tuapp.navlab.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String
)

data class Categoria(
    val id: Int,
    val nombre: String
)

val categorias = listOf(
    Categoria(1, "Electrónica"),
    Categoria(2, "Ropa"),
    Categoria(3, "Hogar"),
    Categoria(4, "Deportes")
)

val productos = listOf(
    Producto(1, "Audífonos", 89.00, "Electrónica"),
    Producto(2, "Smartwatch", 199.00, "Electrónica"),
    Producto(3, "Funda celular", 25.00, "Electrónica"),
    Producto(4, "Camiseta", 45.00, "Ropa"),
    Producto(5, "Zapatillas", 150.00, "Deportes"),
    Producto(6, "Lámpara", 60.00, "Hogar")
)