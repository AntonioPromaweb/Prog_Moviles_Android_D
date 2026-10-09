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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
fun LoginScreen(
    alIniciarSesionExitoso: () -> Unit,
    alIrARegistro: () -> Unit
) {
    val context = LocalContext.current
    var correo by remember { mutableStateOf("juan@correo.com") }
    var contrasena by remember { mutableStateOf("123456") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Bienvenido de nuevo",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Inicia sesión para continuar",
            fontSize = 14.sp,
            color = TextoGris
        )

        Spacer(modifier = Modifier.height(32.dp))

        CampoFormulario(
            icono = Icons.Default.Email,
            etiqueta = "Correo o teléfono",
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
            onValorChange = { contrasena = it },
            placeholder = "••••••••",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            esPassword = true
        )

        Spacer(modifier = Modifier.height(28.dp))

        BotonPrimario(
            texto = "Iniciar sesión",
            onClick = {
                if (correo.isBlank() || contrasena.isBlank()) {
                    Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    val exito = Repositorio.iniciarSesion(correo, contrasena)
                    if (exito) {
                        Toast.makeText(context, "Sesión iniciada con éxito", Toast.LENGTH_SHORT).show()
                        alIniciarSesionExitoso()
                    } else {
                        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "¿No tienes cuenta?", fontSize = 14.sp, color = TextoOscuro)
            TextButton(onClick = alIrARegistro, contentPadding = PaddingValues(horizontal = 6.dp)) {
                Text(
                    text = "Regístrate aquí",
                    color = AzulPrimario,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
