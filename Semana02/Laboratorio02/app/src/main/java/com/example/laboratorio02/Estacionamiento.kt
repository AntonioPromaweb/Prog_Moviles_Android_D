import java.util.Scanner

class Vehiculo(
    val placa: String,
    val tipo: String,
    val horas: Int,
    val esClienteFrecuente: Boolean,
    val nombreCliente: String
) {
    init {
        require(horas >= 1) { "El número de horas debe ser al menos 1." }
    }

    private fun obtenerTarifaBase(): Double {
        return when (tipo.lowercase()) {
            "moto" -> 2.0
            "auto" -> 4.0
            "camioneta" -> 10.0
            else -> 0.0
        }
    }

    fun calcularTotal(): Double {
        val tarifaBase = obtenerTarifaBase()
        var subtotal = 0.0

        for (h in 1..horas) {
            val recargo = when {
                h <= 2 -> 0.0
                h <= 5 -> 0.20
                else -> 0.50
            }
            val costoHora = tarifaBase * (1 + recargo)
            subtotal += costoHora
        }

        val descuento = if (esClienteFrecuente) subtotal * 0.10 else 0.0
        return subtotal - descuento
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

        val descuento = if (esClienteFrecuente) subtotal * 0.10 else 0.0
        val total = subtotal - descuento

        println("----------------------------------------------")
        println(String.format("Subtotal:            S/ %.2f", subtotal))
        println(String.format("Descuento (10%%):     S/ %.2f", descuento))
        println(String.format("Monto Total a Pagar: S/ %.2f", total))
        println("==============================================\n")
    }
}

fun main() {
    val scanner = Scanner(System.`in`)

    println("=== REGISTRO DE VEHÍCULO EN ESTACIONAMIENTO ===")

    print("Ingrese el nombre del cliente: ")
    val nombre = scanner.nextLine()

    print("Ingrese la placa del vehículo: ")
    val placa = scanner.nextLine()

    println("Seleccione el tipo de vehículo:")
    println("1. Moto (S/ 2.00/h)")
    println("2. Auto (S/ 4.00/h)")
    println("3. Camioneta (S/ 10.00/h)")
    print("Opción (1-3): ")
    val opcionTipo = scanner.nextInt()

    val tipo = when (opcionTipo) {
        1 -> "Moto"
        2 -> "Auto"
        3 -> "Camioneta"
        else -> "Auto"
    }

    var horas = 0
    while (horas < 1) {
        print("Ingrese el número de horas estacionado (mínimo 1): ")
        horas = scanner.nextInt()
        if (horas < 1) {
            println("Error: El número de horas debe ser mayor o igual a 1.")
        }
    }

    print("¿Es cliente frecuente? (S/N): ")
    val esFrecuenteInput = scanner.next()
    val esFrecuente = esFrecuenteInput.equals("S", ignoreCase = true)

    val vehiculo = Vehiculo(placa, tipo, horas, esFrecuente, nombre)
    vehiculo.generarBoleta()
}