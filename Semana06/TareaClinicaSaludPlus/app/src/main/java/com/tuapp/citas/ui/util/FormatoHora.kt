package com.tuapp.citas.ui.util

import android.annotation.SuppressLint
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("NewApi")
fun formatearHora(hora: String): String {
    return try {
        val t = LocalTime.parse(hora)
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.forLanguageTag("es-PE"))
        t.format(formatter)
    } catch (_: Exception) {
        hora
    }
}
