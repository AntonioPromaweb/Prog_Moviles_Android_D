package com.tecsup.murocampus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CianNeon,
    onPrimary = FondoEspacial,
    primaryContainer = SuperficieOscura,
    onPrimaryContainer = CianNeon,
    secondary = AmarilloStar,
    onSecondary = FondoEspacial,
    secondaryContainer = SuperficieOscura,
    onSecondaryContainer = AmarilloStar,
    background = FondoEspacial,
    onBackground = TextoClaro,
    surface = SuperficieOscura,
    onSurface = TextoClaro,
    surfaceVariant = SuperficieVariante,
    onSurfaceVariant = TextoClaro,
    error = RojoSable,
    onError = TextoClaro,
    errorContainer = Color(0xFF33080C),
    onErrorContainer = RojoSable,
    outline = CianNeon
)

@Composable
fun MuroCampusTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}