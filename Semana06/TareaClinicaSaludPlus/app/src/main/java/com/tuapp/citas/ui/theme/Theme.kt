package com.tuapp.citas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = AzulPrimario,
    secondary = AzulSecundario,
    background = FondoGris,
    surface = Blanco,
    onPrimary = Blanco,
    onBackground = TextoOscuro,
    onSurface = TextoOscuro
)

@Composable
fun ClinicaSaludPlusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}