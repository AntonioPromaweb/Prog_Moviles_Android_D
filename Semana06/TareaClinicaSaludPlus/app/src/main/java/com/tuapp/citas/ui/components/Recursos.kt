package com.tuapp.citas.ui.components

import androidx.annotation.DrawableRes
import com.tuapp.citas.R

/** Ícono (tarjeta) de cada especialidad según su iconoNombre. */
@DrawableRes
fun iconoEspecialidad(iconoNombre: String): Int = when (iconoNombre) {
    "general" -> R.drawable.ic_esp_general
    "pediatria" -> R.drawable.ic_esp_pediatria
    "ginecologia" -> R.drawable.ic_esp_ginecologia
    "cardiologia" -> R.drawable.ic_esp_cardiologia
    "dermatologia" -> R.drawable.ic_esp_dermatologia
    "traumatologia" -> R.drawable.ic_esp_traumatologia
    else -> R.drawable.ic_esp_oftalmologia
}

/** Ícono que se muestra en "Especialidades destacadas" de Inicio. */
@DrawableRes
fun iconoDestacada(iconoNombre: String): Int = when (iconoNombre) {
    "general" -> R.drawable.ic_dest_general
    "pediatria" -> R.drawable.ic_dest_pediatria
    "ginecologia" -> R.drawable.ic_dest_ginecologia
    else -> iconoEspecialidad(iconoNombre)
}
