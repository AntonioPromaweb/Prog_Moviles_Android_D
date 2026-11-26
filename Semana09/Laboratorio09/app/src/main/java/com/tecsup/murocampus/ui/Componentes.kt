package com.tecsup.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.murocampus.ui.theme.AmarilloStar
import com.tecsup.murocampus.ui.theme.CianNeon
import com.tecsup.murocampus.ui.theme.FondoEspacial
import com.tecsup.murocampus.ui.theme.RojoSable
import com.tecsup.murocampus.ui.theme.SuperficieOscura
import kotlin.random.Random

val AzulMuro = CianNeon

@Composable
fun FondoEstrellas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val random = Random(42L)
        val width = size.width
        val height = size.height
        if (width > 0 && height > 0) {
            repeat(160) {
                val x = random.nextFloat() * width
                val y = random.nextFloat() * height
                val radius = random.nextFloat() * 1.8f + 0.5f
                val alpha = random.nextFloat() * 0.7f + 0.3f
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = radius,
                    center = Offset(x, y)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraMuroCampus() {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "MuroCampus",
                    color = AmarilloStar,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 2.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = SuperficieOscura)
        )
        HorizontalDivider(thickness = 2.dp, color = CianNeon)
    }
}

@Composable
fun Cargando(mensaje: String = "Cargando publicaciones…") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = CianNeon)
        Text(
            text = mensaje,
            color = CianNeon,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(top = 12.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ErrorConReintento(mensaje: String, onReintentar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = Color(0xFF33080C),
            shape = CutCornerShape(8.dp),
            border = BorderStroke(1.dp, RojoSable),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = mensaje,
                color = RojoSable,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
        Button(
            onClick = onReintentar,
            shape = CutCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CianNeon,
                contentColor = FondoEspacial
            ),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text(
                text = "Reintentar",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun TarjetaPost(userId: Int, titulo: String, cuerpo: String, onClick: () -> Unit) {
    val colorEtiqueta = if (userId % 2 == 1) CianNeon else AmarilloStar
    Card(
        onClick = onClick,
        shape = CutCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SuperficieOscura.copy(alpha = 0.85f)),
        border = BorderStroke(1.dp, CianNeon.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Surface(
                color = colorEtiqueta,
                shape = CutCornerShape(4.dp)
            ) {
                Text(
                    text = "Usuario $userId",
                    color = FondoEspacial,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp)
            )
            Text(
                text = cuerpo,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
