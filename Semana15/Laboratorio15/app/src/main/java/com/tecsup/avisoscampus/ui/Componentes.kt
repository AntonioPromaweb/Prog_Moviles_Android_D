package com.tecsup.avisoscampus.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.ui.theme.AmarilloAcento
import com.tecsup.avisoscampus.ui.theme.CianNeon
import com.tecsup.avisoscampus.ui.theme.NegroEspacial
import kotlin.random.Random

private data class Estrella(
    val x: Float,
    val y: Float,
    val radio: Float,
    val alphaBase: Float,
    val fase: Float
)

@Composable
fun FondoEstrellas(modifier: Modifier = Modifier) {
    val estrellas = remember {
        val rand = Random(42)
        List(160) {
            Estrella(
                x = rand.nextFloat(),
                y = rand.nextFloat(),
                radio = rand.nextFloat() * 2.2f + 0.8f,
                alphaBase = rand.nextFloat() * 0.5f + 0.3f,
                fase = rand.nextFloat() * 6.28f
            )
        }
    }

    val transition = rememberInfiniteTransition(label = "TitileoEstrellas")
    val animPhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28318f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "FaseAnimada"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(color = NegroEspacial)
        for (e in estrellas) {
            val alphaCalculado = (e.alphaBase + 0.3f * kotlin.math.sin(e.fase + animPhase))
                .coerceIn(0.1f, 1.0f)
            drawCircle(
                color = Color.White,
                radius = e.radio,
                center = Offset(e.x * size.width, e.y * size.height),
                alpha = alphaCalculado
            )
        }
    }
}

enum class TipoBoton {
    RELLENO,
    CON_BORDE,
    TEXTO
}

@Composable
fun BotonAnimado(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tipo: TipoBoton = TipoBoton.RELLENO,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(stiffness = 500f, dampingRatio = 0.6f),
        label = "BotonAnimadoScale"
    )

    val borderAlpha by animateFloatAsState(
        targetValue = if (isPressed) 1f else 0.6f,
        label = "BotonBorderAlpha"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isPressed) 2.5.dp else 1.5.dp,
        label = "BotonBorderWidth"
    )

    val shape = CutCornerShape(8.dp)

    val modifierAnimado = modifier.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }

    when (tipo) {
        TipoBoton.RELLENO -> {
            Button(
                onClick = onClick,
                modifier = modifierAnimado,
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CianNeon,
                    contentColor = NegroEspacial,
                    disabledContainerColor = Color(0xFF1E2A3A),
                    disabledContentColor = Color(0xFF4E6078)
                ),
                interactionSource = interactionSource,
                content = content
            )
        }
        TipoBoton.CON_BORDE -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifierAnimado,
                enabled = enabled,
                shape = shape,
                border = BorderStroke(
                    borderWidth,
                    if (enabled) CianNeon.copy(alpha = borderAlpha) else Color(0xFF2E3D52)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = CianNeon,
                    disabledContentColor = Color(0xFF4E6078)
                ),
                interactionSource = interactionSource,
                content = content
            )
        }
        TipoBoton.TEXTO -> {
            TextButton(
                onClick = onClick,
                modifier = modifierAnimado,
                enabled = enabled,
                shape = shape,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = AmarilloAcento,
                    disabledContentColor = Color(0xFF4E6078)
                ),
                interactionSource = interactionSource,
                content = content
            )
        }
    }
}
