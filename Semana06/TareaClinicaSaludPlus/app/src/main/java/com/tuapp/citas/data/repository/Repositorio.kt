package com.tuapp.citas.data.repository

import com.tuapp.citas.data.model.Cita
import com.tuapp.citas.data.model.Especialidad
import com.tuapp.citas.data.model.Medico
import com.tuapp.citas.data.model.Usuario

object Repositorio {

    var usuarioActual: Usuario? = null

    private val usuarios = mutableListOf(
        Usuario("u1", "Juan Pérez", "987654321", "juan@correo.com", "123456")
    )

    private val especialidades = listOf(
        Especialidad("esp1", "Medicina General", "Atención primaria integral", "general", esDestacada = true),
        Especialidad("esp2", "Pediatría", "Salud y cuidado infantil", "pediatria", esDestacada = true),
        Especialidad("esp3", "Ginecología", "Salud integral de la mujer", "ginecologia", esDestacada = true),
        Especialidad("esp4", "Cardiología", "Cuidado del corazón", "cardiologia", esDestacada = true),
        Especialidad("esp5", "Dermatología", "Piel, cabello y uñas", "dermatologia", esDestacada = false),
        Especialidad("esp6", "Traumatología", "Huesos y articulaciones", "traumatologia", esDestacada = false),
        Especialidad("esp7", "Oftalmología", "Salud y visión ocular", "oftalmologia", esDestacada = false)
    )

    private val medicos = listOf(
        Medico("m1", "Dra. Ana Torres", "esp3", "CMP: 12345", 4.9, 80.0),
        Medico("m2", "Dra. Claudia Rojas", "esp3", "CMP: 45123", 4.8, 75.0),
        Medico("m3", "Dr. Luis Ramírez", "esp3", "CMP: 67189", 4.7, 70.0),
        Medico("m4", "Dra. Mariana Soto", "esp3", "CMP: 83712", 4.9, 85.0),
        Medico("m5", "Dr. Carlos Mendoza", "esp1", "CMP: 32154", 4.8, 60.0),
        Medico("m6", "Dra. Elena Ramos", "esp2", "CMP: 95412", 4.9, 80.0)
    )

    private val citas = mutableListOf<Cita>()

    val horariosBase = listOf(
        "08:00", "08:30", "09:00",
        "09:30", "10:00", "10:30",
        "11:00", "11:30", "12:00"
    )

    val diasFijosFase1 = listOf("15", "16", "17", "18", "19")

    fun registrarUsuario(nombre: String, telefono: String, correo: String, contrasena: String): Boolean {
        if (usuarios.any { it.correo.equals(correo, ignoreCase = true) }) return false
        val nuevo = Usuario("u${usuarios.size + 1}", nombre, telefono, correo, contrasena)
        usuarios.add(nuevo)
        usuarioActual = nuevo
        return true
    }

    fun iniciarSesion(correo: String, contrasena: String): Boolean {
        val user = usuarios.find { it.correo.equals(correo, ignoreCase = true) && it.contrasena == contrasena }
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

        return horariosBase.filter { it !in horasOcupadas }
    }

    fun agendarCita(medicoId: String, especialidadId: String, fecha: String, hora: String): Boolean {
        val usuario = usuarioActual ?: return false
        val yaExiste = citas.any { it.medicoId == medicoId && it.fecha == fecha && it.hora == hora && it.estado != "Cancelada" }
        if (yaExiste) return false

        val nuevaCita = Cita(
            id = "c${citas.size + 1}",
            usuarioId = usuario.id,
            medicoId = medicoId,
            especialidadId = especialidadId,
            fecha = fecha,
            hora = hora
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