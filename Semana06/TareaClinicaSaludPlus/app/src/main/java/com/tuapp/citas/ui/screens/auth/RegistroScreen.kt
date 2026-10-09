package com.tuapp.citas.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.data.repository.Repositorio
import com.tuapp.citas.ui.components.BotonPrimario
import com.tuapp.citas.ui.components.CampoFormulario
import com.tuapp.citas.ui.theme.AzulOscuro
import com.tuapp.citas.ui.theme.AzulPrimario
import com.tuapp.citas.ui.theme.Blanco
import com.tuapp.citas.ui.theme.TextoGris
import com.tuapp.citas.ui.theme.TextoOscuro

@Composable
fun RegistroScreen(
    alRegistrarExitoso: () -> Unit,
    alIrALogin: () -> Unit
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarTerminos by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Crear cuenta",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Regístrate para agendar tus citas",
            fontSize = 14.sp,
            color = TextoGris
        )

        Spacer(modifier = Modifier.height(28.dp))

        CampoFormulario(
            icono = Icons.Default.Person,
            etiqueta = "Nombre completo",
            valor = nombre,
            onValorChange = { nombre = it.take(50) },
            placeholder = "Juan Pérez"
        )

        Spacer(modifier = Modifier.height(14.dp))

        CampoFormulario(
            icono = Icons.Default.Phone,
            etiqueta = "Teléfono",
            valor = telefono,
            onValorChange = { telefono = it.filter { c -> c.isDigit() }.take(9) },
            placeholder = "987 654 321",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(14.dp))

        CampoFormulario(
            icono = Icons.Default.Email,
            etiqueta = "Correo (opcional)",
            valor = correo,
            onValorChange = { correo = it.trim() },
            placeholder = "juan@correo.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(14.dp))

        CampoFormulario(
            icono = Icons.Default.Lock,
            etiqueta = "Contraseña",
            valor = contrasena,
            onValorChange = { contrasena = it.take(30) },
            placeholder = "••••••••",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            esPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotonPrimario(
            texto = "Registrarme",
            onClick = {
                when {
                    nombre.isBlank() || telefono.isBlank() || contrasena.isBlank() ->
                        Toast.makeText(context, "Completa nombre, teléfono y contraseña", Toast.LENGTH_SHORT).show()
                    nombre.trim().length < 3 || nombre.any { it.isDigit() } ->
                        Toast.makeText(context, "Ingresa un nombre válido (solo letras)", Toast.LENGTH_SHORT).show()
                    !telefono.startsWith("9") || telefono.length != 9 ->
                        Toast.makeText(context, "El teléfono debe tener 9 dígitos y empezar con 9", Toast.LENGTH_SHORT).show()
                    correo.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                        Toast.makeText(context, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
                    contrasena.length < 6 ->
                        Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                    else -> {
                        val exito = Repositorio.registrarUsuario(nombre.trim(), telefono, correo, contrasena)
                        if (exito) {
                            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            alRegistrarExitoso()
                        } else {
                            Toast.makeText(context, "El correo o teléfono ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Al registrarte aceptas nuestros",
            fontSize = 13.sp,
            color = TextoGris,
            textAlign = TextAlign.Center
        )
        TextButton(
            onClick = { mostrarTerminos = true },
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Términos y Condiciones",
                color = AzulPrimario,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "¿Ya tienes cuenta?", fontSize = 14.sp, color = TextoOscuro)
            TextButton(onClick = alIrALogin, contentPadding = PaddingValues(horizontal = 6.dp)) {
                Text(
                    text = "Iniciar sesión",
                    color = AzulPrimario,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    if (mostrarTerminos) {
        AlertDialog(
            onDismissRequest = { mostrarTerminos = false },
            title = { Text("Términos y Condiciones", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Tus datos se usan únicamente para gestionar tus citas médicas en la Clínica SaludPlus. " +
                        "Puedes cancelar una cita desde la sección Mis citas cuando lo necesites."
                )
            },
            confirmButton = {
                TextButton(onClick = { mostrarTerminos = false }) { Text("Entendido") }
            }
        )
    }
}
