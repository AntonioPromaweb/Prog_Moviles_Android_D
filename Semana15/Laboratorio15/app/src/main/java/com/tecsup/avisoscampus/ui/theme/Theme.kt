package com.tecsup.avisoscampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaEspacial = darkColorScheme(
    primary = CianNeon,
    onPrimary = NegroEspacial,
    secondary = AmarilloAcento,
    onSecondary = NegroEspacial,
    tertiary = VioletaApoyo,
    error = RojoPeligro,
    onError = NegroEspacial,
    background = NegroEspacial,
    onBackground = TextoClaro,
    surface = AzulEspacial,
    onSurface = TextoClaro,
    surfaceVariant = Color(0xFF152038),
    onSurfaceVariant = TextoSecundarioEspacial,
    outline = CianNeon
)

@Composable
fun AvisosCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaEspacial,
        typography = Typography,
        content = content
    )
}
