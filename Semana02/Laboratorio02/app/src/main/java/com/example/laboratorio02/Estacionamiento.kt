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
}

fun main() {
    val scanner = Scanner(System.`in`)

    println("=== REGISTRO DE VEHÍCULO EN ESTACIONAMIENTO ===")

    print("Ingrese el nombre del cliente: ")
    val nombre = scanner.nextLine()

    print("Ingrese la placa del vehículo: ")
    val placa = scanner.nextLine()

    println("Seleccione el tipo de vehículo:")
    println("1. Moto")
    println("2. Auto")
    println("3. Camioneta")
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
    println("\n[OK] Vehículo registrado con éxito para el cliente: ${vehiculo.nombreCliente}")
}