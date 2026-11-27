package com.tecsup.avisoscampus.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tecsup.avisoscampus.model.Aviso
import com.tecsup.avisoscampus.model.Origen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface AvisosRepository {
    val avisos: Flow<List<Aviso>>
    suspend fun registrar(titulo: String, texto: String, origen: Origen): Aviso
    suspend fun limpiar()
}

class AvisosRepositoryMemoria : AvisosRepository {
    private val _avisos = MutableStateFlow<List<Aviso>>(emptyList())
    override val avisos: Flow<List<Aviso>> = _avisos.asStateFlow()
    private var siguiente = 1

    override suspend fun registrar(titulo: String, texto: String, origen: Origen): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val aviso = Aviso(siguiente++, titulo, texto, origen, hora)
        _avisos.value = listOf(aviso) + _avisos.value     // lo más reciente arriba
        return aviso
    }

    override suspend fun limpiar() {
        _avisos.value = emptyList()
    }
}

class AvisosRepositoryDataStore(
    private val dataStore: DataStore<Preferences>
) : AvisosRepository {

    private val keyAvisos = stringPreferencesKey("lista_avisos")

    override val avisos: Flow<List<Aviso>> = dataStore.data.map { preferences ->
        val jsonString = preferences[keyAvisos] ?: "[]"
        deserializarAvisos(jsonString)
    }

    override suspend fun registrar(titulo: String, texto: String, origen: Origen): Aviso {
        val hora = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        var nuevoAviso: Aviso? = null

        dataStore.edit { preferences ->
            val jsonString = preferences[keyAvisos] ?: "[]"
            val listaActual = deserializarAvisos(jsonString)
            val maxId = listaActual.maxOfOrNull { it.id } ?: 0
            val nuevoId = maxId + 1

            val aviso = Aviso(nuevoId, titulo, texto, origen, hora)
            nuevoAviso = aviso

            val nuevaLista = (listOf(aviso) + listaActual).take(50)
            preferences[keyAvisos] = serializarAvisos(nuevaLista)
        }

        return nuevoAviso!!
    }

    override suspend fun limpiar() {
        dataStore.edit { preferences ->
            preferences.remove(keyAvisos)
        }
    }

    private fun serializarAvisos(lista: List<Aviso>): String {
        val jsonArray = JSONArray()
        for (aviso in lista) {
            val obj = JSONObject().apply {
                put("id", aviso.id)
                put("titulo", aviso.titulo)
                put("texto", aviso.texto)
                put("origen", aviso.origen.name)
                put("hora", aviso.hora)
            }
            jsonArray.put(obj)
        }
        return jsonArray.toString()
    }

    private fun deserializarAvisos(jsonString: String): List<Aviso> {
        if (jsonString.isBlank()) return emptyList()
        val lista = mutableListOf<Aviso>()
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val id = obj.getInt("id")
            val titulo = obj.getString("titulo")
            val texto = obj.getString("texto")
            val origenStr = obj.getString("origen")
            val origen = Origen.entries.find { it.name == origenStr } ?: Origen.BOTON
            val hora = obj.getString("hora")
            lista.add(Aviso(id, titulo, texto, origen, hora))
        }
        return lista
    }
}
