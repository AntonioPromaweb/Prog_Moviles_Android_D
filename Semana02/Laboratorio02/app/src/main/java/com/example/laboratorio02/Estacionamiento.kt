package com.example.laboratorio02

import java.util.Scanner

class Vehiculo(
    val placa: String,
    val tipo: String,
    val horas: Int,
    val esClienteFrecuente: Boolean,
    val nombreCliente: String
) {
    init {
        require(horas in 1..24) { "El número de horas debe estar entre 1 y 24." }
    }

    private fun obtenerTarifaBase(): Double {
        return when (tipo.lowercase()) {
            "moto" -> 2.0
            "auto", "vehiculo" -> 4.0
            "camioneta" -> 10.0
            "trailer" -> 20.0
            else -> 0.0
        }
    }

    fun generarBoleta() {
        val tarifaBase = obtenerTarifaBase()
        println("\n==============================================")
        println("             BOLETA DE ESTACIONAMIENTO        ")
        println("==============================================")
        println("Cliente: $nombreCliente")
        println("Placa  : $placa")
        println("Tipo   : $tipo")
        println("Horas  : $horas")
        println("----------------------------------------------")
        println(String.format("%-6s | %-11s | %-9s | %-10s", "Hora", "Tarifa Base", "% Recargo", "Importe"))
        println("----------------------------------------------")

        var subtotal = 0.0
        for (h in 1..horas) {
            val recargoPorcentaje = when {
                h <= 2 -> 0
                h <= 5 -> 20
                h <= 10 -> 40
                else -> 50
            }
            val recargoDecimal = recargoPorcentaje / 100.0
            val costoHora = tarifaBase * (1 + recargoDecimal)
            subtotal += costoHora

            println(
                String.format(
                    "Hora %-2d | S/ %-9.2f | %-8s | S/ %-8.2f",
                    h, tarifaBase, "$recargoPorcentaje%", costoHora
                )
            )
        }

        val igv = subtotal * 0.18
        val totalConIgv = subtotal + igv

        val descFrecuente = if (esClienteFrecuente) totalConIgv * 0.10 else 0.0
        val totalDespuesFrecuente = totalConIgv - descFrecuente

        val descMonto = if (totalDespuesFrecuente > 500.0) totalDespuesFrecuente * 0.20 else 0.0
        val totalFinal = totalDespuesFrecuente - descMonto

        println("----------------------------------------------")
        println(String.format("%-25s: S/ %.2f", "Subtotal", subtotal))
        println(String.format("%-25s: S/ %.2f", "IGV (18%)", igv))
        if (descFrecuente > 0) {
            println(String.format("%-25s: S/ %.2f", "Desc. Cliente Frec. (10%)", descFrecuente))
        }
        if (descMonto > 0) {
            println(String.format("%-25s: S/ %.2f", "Desc. Monto > S/500 (20%)", descMonto))
        }
        println(String.format("%-25s: S/ %.2f", "Monto Total a Pagar", totalFinal))
        println("==============================================\n")
    }
}

fun mostrarConteoPorTipo(lista: List<Vehiculo>) {
    val motos = lista.count { it.tipo.lowercase() == "moto" }
    val autos = lista.count { it.tipo.lowercase() in listOf("vehiculo", "auto") }
    val camionetas = lista.count { it.tipo.lowercase() == "camioneta" }
    val trailers = lista.count { it.tipo.lowercase() == "trailer" }

    println("\n==============================================")
    println("      RESUMEN DE VEHÍCULOS POR CATEGORÍA      ")
    println("==============================================")
    println("  • Motos      : $motos")
    println("  • Autos/Veh. : $autos")
    println("  • Camionetas : $camionetas")
    println("  • Trailers   : $trailers")
    println("----------------------------------------------")
    println("  TOTAL REGISTRADOS: ${lista.size}")
    println("==============================================\n")
}

fun main() {
    val scanner = Scanner(System.`in`)
    val listaVehiculos = mutableListOf<Vehiculo>()

    var aforoMaximo = 0
    while (aforoMaximo < 1) {
        println("=== CONFIGURACIÓN DE SISTEMA DE ESTACIONAMIENTO ===")
        print("Ingrese la capacidad máxima de aforo de vehículos (mínimo 1): ")
        val entrada = scanner.nextLine().trim()
        aforoMaximo = entrada.toIntOrNull() ?: 0
        if (aforoMaximo < 1) {
            println("\n¡ERROR! Ingrese un número entero válido mayor a 0.\n")
        }
    }

    var opcionMenu = 0
    do {
        println("\n=== MENÚ DE CONTROL (Aforo: ${listaVehiculos.size}/$aforoMaximo) ===")
        println("1. Registrar nuevo vehículo")
        println("2. Ver lista y conteo por tipo de vehículo")
        println("3. Salir")
        print("Seleccione una opción: ")

        val entradaMenu = scanner.nextLine().trim()
        opcionMenu = entradaMenu.toIntOrNull() ?: 0

        when (opcionMenu) {
            1 -> {
                if (listaVehiculos.size >= aforoMaximo) {
                    println("\n¡ERROR! Se ha alcanzado el aforo máximo permitido ($aforoMaximo vehículos).")
                    mostrarConteoPorTipo(listaVehiculos)
                } else {
                    println("\n--- REGISTRO DE VEHÍCULO #${listaVehiculos.size + 1} ---")

                    var nombre = ""
                    while (nombre.isEmpty()) {
                        print("Ingrese el nombre del cliente: ")
                        nombre = scanner.nextLine().trim()
                        if (nombre.isEmpty()) {
                            println("¡ERROR! El nombre no puede estar vacío.")
                        }
                    }

                    var placa = ""
                    while (placa.isEmpty()) {
                        print("Ingrese la placa del vehículo: ")
                        placa = scanner.nextLine().trim()
                        if (placa.isEmpty()) {
                            println("¡ERROR! La placa no puede estar vacía.")
                        }
                    }

                    var tipo = ""
                    while (tipo.isEmpty()) {
                        println("\nSeleccione el tipo de vehículo:")
                        println("1. Moto (S/ 2.00/h)")
                        println("2. Vehículo / Auto (S/ 4.00/h)")
                        println("3. Camioneta (S/ 10.00/h)")
                        println("4. Trailer (S/ 20.00/h)")
                        print("Opción (1-4): ")

                        val entradaTipo = scanner.nextLine().trim()
                        val opcionNum = entradaTipo.toIntOrNull()

                        tipo = when (opcionNum) {
                            1 -> "Moto"
                            2 -> "Vehiculo"
                            3 -> "Camioneta"
                            4 -> "Trailer"
                            else -> {
                                println("\n¡ERROR! Opción inválida. Solo se aceptan números del 1 al 4. Intente nuevamente.")
                                ""
                            }
                        }
                    }

                    var horas = 0
                    while (horas !in 1..24) {
                        print("Ingrese el número de horas estacionado (mínimo 1 - máximo 24): ")
                        val entradaHoras = scanner.nextLine().trim()
                        horas = entradaHoras.toIntOrNull() ?: 0
                        if (horas !in 1..24) {
                            println("\n¡ERROR! El tiempo límite comercial permitido es de 1 a 24 horas. Ingrese un valor válido.\n")
                        }
                    }

                    var esFrecuente = false
                    var respuestaValida = false
                    while (!respuestaValida) {
                        print("¿Es cliente frecuente? (S/N): ")
                        val esFrecuenteInput = scanner.nextLine().trim()

                        if (esFrecuenteInput.equals("S", ignoreCase = true)) {
                            esFrecuente = true
                            respuestaValida = true
                        } else if (esFrecuenteInput.equals("N", ignoreCase = true)) {
                            esFrecuente = false
                            respuestaValida = true
                        } else {
                            println("\n¡ERROR! Opción inválida. Ingrese solo 'S' para Sí o 'N' para No.\n")
                        }
                    }

                    val nuevoVehiculo = Vehiculo(placa, tipo, horas, esFrecuente, nombre)
                    listaVehiculos.add(nuevoVehiculo)

                    println("\n--- BOLETA GENERADA PARA EL VEHÍCULO REGISTRADO ---")
                    nuevoVehiculo.generarBoleta()

                    if (listaVehiculos.size == aforoMaximo) {
                        println("¡SE HA LLENADO EL AFORO MÁXIMO DEL ESTACIONAMIENTO!")
                        mostrarConteoPorTipo(listaVehiculos)
                    }
                }
            }

            2 -> {
                if (listaVehiculos.isEmpty()) {
                    println("\nNo hay vehículos registrados aún.")
                } else {
                    println("\n==================================================")
                    println("       LISTA DE VEHÍCULOS EN ESTACIONAMIENTO      ")
                    println("==================================================")
                    println(String.format("%-3s | %-10s | %-10s | %-10s | %-6s", "N°", "Placa", "Tipo", "Cliente", "Horas"))
                    println("--------------------------------------------------")
                    listaVehiculos.forEachIndexed { index, v ->
                        println(String.format("%-3d | %-10s | %-10s | %-10s | %-6d", index + 1, v.placa, v.tipo, v.nombreCliente, v.horas))
                    }
                    mostrarConteoPorTipo(listaVehiculos)
                }
            }

            3 -> {
                println("\nSaliendo del sistema...")
                if (listaVehiculos.isNotEmpty()) {
                    mostrarConteoPorTipo(listaVehiculos)
                }
            }
            else -> println("\n¡ERROR! Opción del menú no válida. Elija 1, 2 o 3.")
        }
    } while (opcionMenu != 3)
}