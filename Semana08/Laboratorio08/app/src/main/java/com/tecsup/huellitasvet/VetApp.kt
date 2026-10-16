package com.tecsup.huellitasvet

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VetApp : Application() {

    val database: VetDatabase by lazy { VetDatabase.obtener(this) }

    // La app solo conoce la interface; la implementacion con Room queda aqui.
    val repository: VetRepository by lazy { VetRepositoryRoom(database) }

    // Preferencias del dispositivo (perfil activo, modo oscuro, etc.)
    val prefs: PreferenciasStore by lazy { PreferenciasStore(this) }

    override fun onCreate() {
        super.onCreate()
        // Siembra los datos de prueba la primera vez (en un hilo de IO)
        CoroutineScope(Dispatchers.IO).launch {
            DatosIniciales.sembrar(database)
        }
    }
}