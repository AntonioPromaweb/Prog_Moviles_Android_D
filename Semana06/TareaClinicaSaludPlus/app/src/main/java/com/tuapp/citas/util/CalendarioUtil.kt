package com.tuapp.citas.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val LOCALE_ES = Locale("es", "PE")

/** Sábado y domingo no son días de atención. */
fun esDiaHabil(fecha: LocalDate): Boolean =
    fecha.dayOfWeek != DayOfWeek.SATURDAY && fecha.dayOfWeek != DayOfWeek.SUNDAY

/** Primer día hábil a partir de [desde] (si es fin de semana salta al lunes). */
fun primerDiaHabil(desde: LocalDate): LocalDate {
    var dia = desde
    while (!esDiaHabil(dia)) dia = dia.plusDays(1)
    return dia
}

/**
 * Devuelve [cantidad] días hábiles consecutivos de la semana [semanaOffset].
 * La semana 0 arranca hoy (o el próximo día hábil); cada offset adelanta 7 días,
 * por lo que nunca se muestran días pasados.
 */
fun diasHabilesDeSemana(
    semanaOffset: Int,
    hoy: LocalDate = LocalDate.now(),
    cantidad: Int = 5
): List<LocalDate> {
    val inicio = primerDiaHabil(hoy).plusWeeks(semanaOffset.coerceAtLeast(0).toLong())
    val dias = mutableListOf<LocalDate>()
    var cursor = inicio
    while (dias.size < cantidad) {
        if (esDiaHabil(cursor)) dias.add(cursor)
        cursor = cursor.plusDays(1)
    }
    return dias
}

/** "Lun", "Mar", "Mié"... */
fun nombreCortoDia(fecha: LocalDate): String =
    fecha.dayOfWeek.getDisplayName(TextStyle.SHORT, LOCALE_ES)
        .replace(".", "")
        .replaceFirstChar { it.uppercase() }

/** "Octubre 2026" */
fun mesYAnio(fecha: LocalDate): String =
    "${nombreMes(fecha)} ${fecha.year}".replaceFirstChar { it.uppercase() }

/** "Martes 16 de septiembre 2026" */
fun fechaLargaEnEspanol(fecha: LocalDate): String {
    val dia = fecha.dayOfWeek.getDisplayName(TextStyle.FULL, LOCALE_ES)
    return "$dia ${fecha.dayOfMonth} de ${nombreMes(fecha)} ${fecha.year}"
        .replaceFirstChar { it.uppercase() }
}

/** Convierte "2026-09-16" a texto largo; si el formato no es válido devuelve el original. */
fun fechaLargaDesdeIso(iso: String): String =
    try {
        fechaLargaEnEspanol(LocalDate.parse(iso))
    } catch (e: Exception) {
        iso
    }

private fun nombreMes(fecha: LocalDate): String =
    fecha.month.getDisplayName(TextStyle.FULL, LOCALE_ES)
