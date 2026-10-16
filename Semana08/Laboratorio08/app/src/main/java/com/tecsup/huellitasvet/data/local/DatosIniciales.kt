package com.tecsup.huellitasvet.data.local

import com.tecsup.huellitasvet.data.local.entity.Cita
import com.tecsup.huellitasvet.data.local.entity.Mascota
import com.tecsup.huellitasvet.data.local.entity.Servicio
import com.tecsup.huellitasvet.data.local.entity.Usuario
import com.tecsup.huellitasvet.model.Especie
import com.tecsup.huellitasvet.model.EstadoCita
import com.tecsup.huellitasvet.model.Rol
import com.tecsup.huellitasvet.model.Sexo
import kotlinx.coroutines.flow.first
import java.time.LocalDate

suspend fun sembrar(db: VetDatabase) {
    if (db.usuarioDao().contar() > 0) return

    // Servicios
    val servicioDao = db.servicioDao()
    if (servicioDao.contar() == 0) {
        servicioDao.insertarTodos(
            listOf(
                Servicio(nombre = "Consulta general", descripcion = "Revisión general", duracionMin = 30, precio = 60.0, activo = true, icono = "heart"),
                Servicio(nombre = "Vacunación", descripcion = "Aplicación de vacunas", duracionMin = 30, precio = 45.0, activo = true, icono = "syringe"),
                Servicio(nombre = "Baño y corte", descripcion = "Higiene general", duracionMin = 60, precio = 50.0, activo = true, icono = "scissors"),
                Servicio(nombre = "Desparasitación", descripcion = "Tratamiento antiparasitario", duracionMin = 15, precio = 25.0, activo = false, icono = "heart")
            )
        )
    }

    val guardados = servicioDao.observarTodos().first()
    val consulta = guardados.first { it.nombre == "Consulta general" }
    val vacuna = guardados.first { it.nombre == "Vacunación" }
    val bano = guardados.first { it.nombre == "Baño y corte" }

    // Usuarios
    val usuarioDao = db.usuarioDao()
    usuarioDao.insertarTodos(
        listOf(
            Usuario(nombre = "Ana Torres", correo = "ana@correo.test", clave = "1234", rol = Rol.CLIENTE.name),
            Usuario(nombre = "Luis Vega", correo = "luis@correo.test", clave = "1234", rol = Rol.CLIENTE.name),
            Usuario(nombre = "Dra. Rojas", correo = "vet@huellitas.test", clave = "admin", rol = Rol.ADMIN.name)
        )
    )
    val ana = usuarioDao.login("ana@correo.test", "1234")!!
    val luis = usuarioDao.login("luis@correo.test", "1234")!!

    // Mascotas
    val mascotaDao = db.mascotaDao()
    val luna = mascotaDao.insertar(Mascota(duenoId = ana.id, nombre = "Luna", especie = Especie.GATO.name, raza = "Siamesa", anioNacimiento = LocalDate.now().year - 3, peso = 4.3, sexo = Sexo.HEMBRA.name)).toInt()
    val max = mascotaDao.insertar(Mascota(duenoId = ana.id, nombre = "Max", especie = Especie.PERRO.name, raza = "Labrador", anioNacimiento = LocalDate.now().year - 5, peso = 24.5, sexo = Sexo.MACHO.name)).toInt()
    val rocky = mascotaDao.insertar(Mascota(duenoId = luis.id, nombre = "Rocky", especie = Especie.PERRO.name, raza = "Bulldog", anioNacimiento = LocalDate.now().year - 6, peso = 23.8, sexo = Sexo.MACHO.name)).toInt()
    val michi = mascotaDao.insertar(Mascota(duenoId = luis.id, nombre = "Michi", especie = Especie.GATO.name, raza = "Mestizo", anioNacimiento = LocalDate.now().year - 2, peso = 3.9, sexo = Sexo.MACHO.name)).toInt()

    fun dia(n: Long): String = LocalDate.now().plusDays(n).toString()

    val citaDao = db.citaDao()
    val citas = listOf(
        Cita(mascotaId = luna, servicioId = vacuna.id, fecha = dia(-10), hora = "10:00", estado = EstadoCita.ATENDIDA.name, precio = vacuna.precio, diagnostico = "Vacuna triple felina", tratamiento = "Refuerzo en 12 meses", pesoRegistrado = 4.3),
        Cita(mascotaId = luna, servicioId = consulta.id, fecha = dia(-100), hora = "09:30", estado = EstadoCita.ATENDIDA.name, precio = consulta.precio, diagnostico = "Control de rutina, sin hallazgos", tratamiento = "Ninguno", pesoRegistrado = 4.2),
        Cita(mascotaId = luna, servicioId = bano.id, fecha = dia(-200), hora = "11:00", estado = EstadoCita.ATENDIDA.name, precio = bano.precio, diagnostico = "Higiene general", tratamiento = "Baño medicado suave", pesoRegistrado = 4.0),
        Cita(mascotaId = rocky, servicioId = consulta.id, fecha = dia(-30), hora = "14:00", estado = EstadoCita.ATENDIDA.name, precio = consulta.precio, diagnostico = "Leve dermatitis", tratamiento = "Shampoo especial 2 semanas", pesoRegistrado = 23.8),
        Cita(mascotaId = max, servicioId = bano.id, fecha = dia(0), hora = "09:00", estado = EstadoCita.ATENDIDA.name, precio = bano.precio, diagnostico = "Higiene general", tratamiento = "Corte de pelo", pesoRegistrado = 24.5),
        Cita(mascotaId = rocky, servicioId = consulta.id, fecha = dia(0), hora = "11:00", estado = EstadoCita.PENDIENTE.name, precio = consulta.precio),
        Cita(mascotaId = michi, servicioId = bano.id, fecha = dia(0), hora = "12:30", estado = EstadoCita.PENDIENTE.name, precio = bano.precio),
        Cita(mascotaId = luna, servicioId = vacuna.id, fecha = dia(1), hora = "10:30", estado = EstadoCita.CONFIRMADA.name, precio = vacuna.precio),
        Cita(mascotaId = max, servicioId = consulta.id, fecha = dia(2), hora = "10:00", estado = EstadoCita.PENDIENTE.name, precio = consulta.precio),
        Cita(mascotaId = michi, servicioId = vacuna.id, fecha = dia(3), hora = "09:30", estado = EstadoCita.CANCELADA.name, precio = vacuna.precio)
    )
    citas.forEach { citaDao.insertar(it) }
}