package com.tuapp.citas.data.model

data class Cita(
    val id: String,
    val usuarioId: String,
    val medicoId: String,
    val especialidadId: String,
    val fecha: String,
    val hora: String,
    val estado: String = "Confirmada",
    val motivo: String = ""
)