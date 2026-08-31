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
    println("\n[OK] Vehículo registrado.")
    println(String.format("Monto preliminar a pagar: S/ %.2f", vehiculo.calcularTotal()))
}