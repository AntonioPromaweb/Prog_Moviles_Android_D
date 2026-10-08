package com.tuapp.citas.data.model

data class Especialidad(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val iconoNombre: String,
    val esDestacada: Boolean = false
)