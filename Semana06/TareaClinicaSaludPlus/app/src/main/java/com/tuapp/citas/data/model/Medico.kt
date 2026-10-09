package com.tuapp.citas.data.model

data class Medico(
    val id: String,
    val nombre: String,
    val especialidadId: String,
    val cmp: String,
    val calificacion: Double,
    val precioConsulta: Double,
    val fotoRes: Int = 0,
    val cargo: String = "",
    val resenas: Int = 0,
    val disponibilidad: String = ""
)
