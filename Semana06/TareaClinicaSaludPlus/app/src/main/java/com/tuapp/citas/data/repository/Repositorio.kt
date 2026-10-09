package com.tuapp.citas.data.repository

import com.tuapp.citas.R
import com.tuapp.citas.data.model.Cita
import com.tuapp.citas.data.model.Especialidad
import com.tuapp.citas.data.model.Medico
import com.tuapp.citas.data.model.Sede
import com.tuapp.citas.data.model.Usuario
import java.time.LocalDate
import java.time.LocalTime

object Repositorio {

    var usuarioActual: Usuario? = null

    private val usuarios = mutableListOf(
        Usuario("u1", "Juan Pérez", "987654321", "juan@correo.com", "123456")
    )

    private val sedes = listOf(
        Sede("s1", "Sede San Juan de Lurigancho", "Av. Próceres de la Independencia 1542", "(01) 456-7890", "San Juan de Lurigancho"),
        Sede("s2", "Sede La Molina", "Av. La Molina 3820", "(01) 349-1234", "La Molina"),
        Sede("s3", "Sede Santa Anita", "Av. Los Chancas 120", "(01) 362-9876", "Santa Anita"),
        Sede("s4", "Sede Ate", "Carretera Central Km. 7.5", "(01) 351-5555", "Ate"),
        Sede("s5", "Sede Surco", "Av. Primavera 1250", "(01) 437-8899", "Santiago de Surco")
    )

    private val especialidades = listOf(
        Especialidad("esp1", "Medicina General", "Atención integral", "general", esDestacada = true),
        Especialidad("esp2", "Pediatría", "Niños y adolescentes", "pediatria", esDestacada = true),
        Especialidad("esp3", "Ginecología", "Salud de la mujer", "ginecologia", esDestacada = true),
        Especialidad("esp4", "Cardiología", "Corazón y vasos sanguíneos", "cardiologia", esDestacada = true),
        Especialidad("esp5", "Dermatología", "Piel, cabello y uñas", "dermatologia", esDestacada = false),
        Especialidad("esp6", "Traumatología", "Huesos y articulaciones", "traumatologia", esDestacada = false),
        Especialidad("esp7", "Oftalmología", "Salud visual", "oftalmologia", esDestacada = false)
    )

    // Médicos con fotos asignadas según requerimiento
    private val medicos = listOf(
        Medico("m1", "Dra. Ana Torres", "esp3", "CMP: 12345", 4.8, 80.0,
            R.drawable.foto_ana_torres, "Ginecóloga", 120, "Disponible hoy"),
        Medico("m2", "Dra. Claudia Rojas", "esp3", "CMP: 45123", 4.8, 75.0,
            R.drawable.foto_claudia_rojas, "Ginecóloga", 96, "Disponible mañana"),
        Medico("m3", "Dr. Luis Ramírez", "esp1", "CMP: 67189", 4.7, 60.0,
            R.drawable.foto_luis_ramirez, "Médico general", 88, "Disponible hoy"),
        Medico("m4", "Dra. Mariana Soto", "esp1", "CMP: 83712", 4.6, 60.0,
            R.drawable.foto_mariana_soto, "Médica general", 76, "Disponible esta semana"),
        Medico("m5", "Dr. Carlos Mendoza", "esp4", "CMP: 32154", 4.8, 95.0,
            R.drawable.foto_ana_torres, "Cardiólogo", 110, "Disponible hoy"),
        Medico("m6", "Dra. Elena Ramos", "esp2", "CMP: 95412", 4.9, 70.0,
            R.drawable.foto_claudia_rojas, "Pediatra", 134, "Disponible mañana"),
        Medico("m7", "Dra. Valeria Núñez", "esp5", "CMP: 41876", 4.7, 85.0,
            R.drawable.foto_mariana_soto, "Dermatóloga", 64, "Disponible esta semana"),
        Medico("m8", "Dr. Jorge Paredes", "esp5", "CMP: 52903", 4.5, 80.0,
            R.drawable.foto_luis_ramirez, "Dermatólogo", 52, "Disponible mañana"),
        Medico("m9", "Dr. Ricardo Salas", "esp6", "CMP: 70418", 4.6, 90.0,
            R.drawable.foto_luis_ramirez, "Traumatólogo", 71, "Disponible hoy"),
        Medico("m10", "Dra. Rosa Medina", "esp7", "CMP: 63527", 4.7, 85.0,
            R.drawable.foto_claudia_rojas, "Oftalmóloga", 58, "Disponible esta semana")
    )

    private val citas = mutableListOf<Cita>()

    val horariosBase = listOf(
        "06:00", "08:30", "09:00",
        "09:30", "10:00", "10:30",
        "11:00", "11:30", "12:00"
    )

    val diasFijosFase1 = listOf("15", "16", "17", "18", "19")

    fun obtenerSedes(): List<Sede> = sedes

    fun obtenerSede(id: String): Sede? = sedes.find { it.id == id }

    fun todosLosMedicos(): List<Medico> = medicos

    fun registrarUsuario(nombre: String, telefono: String, correo: String, contrasena: String): Boolean {
        if (correo.isNotBlank() && usuarios.any { it.correo.equals(correo, ignoreCase = true) }) return false
        if (usuarios.any { it.telefono == telefono }) return false
        val nuevo = Usuario("u${usuarios.size + 1}", nombre, telefono, correo, contrasena)
        usuarios.add(nuevo)
        usuarioActual = nuevo
        return true
    }

    fun iniciarSesion(correo: String, contrasena: String): Boolean {
        val user = usuarios.find {
            (it.correo.equals(correo, ignoreCase = true) || it.telefono == correo) && it.contrasena == contrasena
        }
        if (user != null) {
            usuarioActual = user
            return true
        }
        return false
    }

    fun cerrarSesion() {
        usuarioActual = null
    }

    fun buscarEspecialidades(query: String): List<Especialidad> {
        if (query.isBlank()) return especialidades
        return especialidades.filter { it.nombre.contains(query, ignoreCase = true) }
    }

    fun especialidadesDestacadas(): List<Especialidad> {
        return especialidades.filter { it.esDestacada }.take(4)
    }

    fun obtenerEspecialidad(id: String): Especialidad? = especialidades.find { it.id == id }

    fun medicosPorEspecialidad(especialidadId: String): List<Medico> {
        return medicos.filter { it.especialidadId == especialidadId }
            .sortedByDescending { it.calificacion }
    }

    fun buscarMedicos(especialidadId: String, query: String): List<Medico> {
        val lista = medicosPorEspecialidad(especialidadId)
        if (query.isBlank()) return lista
        return lista.filter { it.nombre.contains(query, ignoreCase = true) }
    }

    fun obtenerMedico(id: String): Medico? = medicos.find { it.id == id }

    fun horariosDisponibles(medicoId: String, fecha: String): List<String> {
        val horasOcupadas = citas
            .filter { it.medicoId == medicoId && it.fecha == fecha && it.estado != "Cancelada" }
            .map { it.hora }

        val esHoy = fecha == LocalDate.now().toString()
        val ahora = LocalTime.now()
        return horariosBase.filter { h ->
            h !in horasOcupadas && !(esHoy && LocalTime.parse(h) <= ahora)
        }
    }

    fun agendarCita(medicoId: String, especialidadId: String, fecha: String, hora: String, motivo: String = ""): Boolean {
        val usuario = usuarioActual ?: return false
        val yaExiste = citas.any { it.medicoId == medicoId && it.fecha == fecha && it.hora == hora && it.estado != "Cancelada" }
        if (yaExiste) return false
        val conflictoPaciente = citas.any {
            it.usuarioId == usuario.id && it.fecha == fecha && it.hora == hora && it.estado != "Cancelada"
        }
        if (conflictoPaciente) return false
        val fechaHora = try {
            LocalDate.parse(fecha).atTime(LocalTime.parse(hora))
        } catch (e: Exception) {
            return false
        }
        if (fechaHora.isBefore(java.time.LocalDateTime.now())) return false

        val nuevaCita = Cita(
            id = "c${citas.size + 1}",
            usuarioId = usuario.id,
            medicoId = medicoId,
            especialidadId = especialidadId,
            fecha = fecha,
            hora = hora,
            motivo = motivo
        )
        citas.add(nuevaCita)
        return true
    }

    fun citasDelUsuario(): List<Cita> {
        val id = usuarioActual?.id ?: return emptyList()
        return citas.filter { it.usuarioId == id }
    }

    fun obtenerCita(citaId: String): Cita? = citas.find { it.id == citaId }

    fun cancelarCita(citaId: String): Boolean {
        return citas.removeIf { it.id == citaId }
    }
}
