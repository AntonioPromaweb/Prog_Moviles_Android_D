package com.tuapp.citas.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuapp.citas.R
import com.tuapp.citas.ui.components.BotonPrimario
import com.tuapp.citas.ui.theme.AzulOscuro
import com.tuapp.citas.ui.theme.AzulPrimario
import com.tuapp.citas.ui.theme.FondoSplash
import com.tuapp.citas.ui.theme.TextoGris

@Composable
fun SplashScreen(
    alIrARegistro: () -> Unit,
    alIrALogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSplash)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Logo SaludPlus",
            modifier = Modifier.size(width = 96.dp, height = 90.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Clínica",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )
        Text(
            text = "SaludPlus",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AzulOscuro
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Tu salud, nuestra prioridad",
            fontSize = 16.sp,
            color = TextoGris
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.img_doctor_splash),
            contentDescription = "Doctor de la clínica",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(FondoSplash)
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BotonPrimario(
                texto = "Comenzar",
                onClick = alIrARegistro
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = alIrALogin) {
                Text(
                    text = "Ya tengo una cuenta",
                    color = AzulPrimario,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
