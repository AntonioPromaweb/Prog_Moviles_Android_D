package com.tecsup.huellitasvet.local1.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class Servicio(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val duracionMin: Int,
    val precio: Double,
    val activo: Boolean = true,
    val icono: String
)