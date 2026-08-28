# Programación Móvil Android - Laboratorio 02

**Proyecto:** Carrito de Compras en Kotlin (Versión Tradicional - Sin IA)  
**Estudiante:** Luis Antonio Vasquez Flores

---

## Descripción del Programa
El programa simula el funcionamiento de un carrito de compras por consola en Kotlin, gestionando productos con sus respectivos nombres, precios y cantidades. Genera un reporte detallado con columnas alineadas usando `String.format`, calcula el subtotal, IGV (18%), determina el producto más caro y aplica un descuento según el monto acumulado para mostrar el total final a pagar.



```kotlin
data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

Respuestas Teóricas
/// ¿Por qué nombre y precio son val pero cantidad es var? ///
Precio y nombre son val porque son variables inmutables; representan datos fijos del producto que no deben reasignarse en la sesión. cantidad es una variable mutable (var) porque es un dato dinámico que puede cambiar según los ítems agregados o modificados.

/// ¿Qué pasaría si intentas cambiar el precio después de crear el producto? ///
Al ser precio una propiedad inmutable (val), Kotlin prohíbe reasignar su valor directamente. El código no compilará y el IDE generará un error en tiempo de compilación.