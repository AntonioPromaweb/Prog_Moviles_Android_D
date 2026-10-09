package com.tuapp.citas.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.ui.theme.AzulClaro
import com.tuapp.citas.ui.theme.AzulOscuro
import com.tuapp.citas.ui.theme.AzulPrimario
import com.tuapp.citas.ui.theme.Blanco
import com.tuapp.citas.ui.theme.BordeGris
import com.tuapp.citas.ui.theme.FondoCampo
import com.tuapp.citas.ui.theme.RojoAlerta
import com.tuapp.citas.ui.theme.TextoGris
import com.tuapp.citas.ui.theme.TextoOscuro
import com.tuapp.citas.ui.theme.VerdeChipFondo
import com.tuapp.citas.ui.theme.VerdeChipTexto

@Composable
fun BotonPrimario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AzulPrimario,
            contentColor = Blanco
        )
    ) {
        Text(text = texto, fontWeight = FontWeight.SemiBold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperiorConVolver(
    titulo: String,
    alVolver: () -> Unit,
    acciones: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AzulOscuro
            )
        },
        navigationIcon = {
            IconButton(onClick = alVolver) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = AzulOscuro
                )
            }
        },
        actions = acciones,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

/** Campo del diseño: cuadro con ícono a la izquierda, etiqueta pequeña y valor debajo. */
@Composable
fun CampoFormulario(
    icono: ImageVector,
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    esPassword: Boolean = false
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Blanco,
        border = BorderStroke(1.dp, BordeGris)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AzulClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = AzulPrimario, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = etiqueta, fontSize = 12.sp, color = TextoGris)
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(FondoCampo)
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    if (valor.isEmpty() && placeholder.isNotEmpty()) {
                        Text(text = placeholder, fontSize = 15.sp, color = TextoGris.copy(alpha = 0.6f))
                    }
                    BasicTextField(
                        value = valor,
                        onValueChange = onValorChange,
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 15.sp, color = TextoOscuro),
                        cursorBrush = SolidColor(AzulPrimario),
                        keyboardOptions = keyboardOptions,
                        visualTransformation = if (esPassword) PasswordVisualTransformation() else VisualTransformation.None,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

/** Foto circular del médico (recorta para llenar el círculo). */
@Composable
fun FotoMedico(
    fotoRes: Int,
    tamano: Dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = fotoRes),
        contentDescription = "Foto del médico",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(tamano)
            .clip(CircleShape)
    )
}

/** Etiqueta verde "Disponible hoy / mañana / esta semana". */
@Composable
fun ChipDisponibilidad(texto: String) {
    Surface(
        color = VerdeChipFondo,
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = texto,
            color = VerdeChipTexto,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun DialogoConfirmacion(
    mostrar: Boolean,
    titulo: String,
    mensaje: String,
    onConfirmar: () -> Unit,
    onDescartar: () -> Unit
) {
    if (mostrar) {
        AlertDialog(
            onDismissRequest = onDescartar,
            title = {
                Text(text = titulo, fontWeight = FontWeight.Bold, color = TextoOscuro)
            },
            text = {
                Text(text = mensaje, color = TextoGris)
            },
            confirmButton = {
                TextButton(onClick = onConfirmar) {
                    Text(text = "Confirmar", color = RojoAlerta, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = onDescartar) {
                    Text(text = "Cancelar", color = TextoGris)
                }
            },
            containerColor = Blanco,
            shape = RoundedCornerShape(14.dp)
        )
    }
}
