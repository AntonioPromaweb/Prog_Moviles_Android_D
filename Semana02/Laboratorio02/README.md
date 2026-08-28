# Laboratorio 02: Carrito de Compras en Kotlin (Versión con IA)

**Curso:** Programación Móvil Android  
**Estudiante:** Luis Antonio Vasquez Flores

---

## Explicación del Proyecto
Programa desarrollado en Kotlin refactorizado bajo el paradigma de la Programación Orientada a Objetos (POO). El sistema administra un carrito de compras, calcula subtotales, IGV (18%), total a pagar, descuentos aplicados mediante `when` y la detección del producto más caro.

---

## Prompt Utilizado para la IA

[ROL]  
Eres un desarrollador Senior especializado en Kotlin y Programación Orientada a Objetos (POO), con experiencia dictando laboratorios universitarios.

[CONTEXTO]  
Proyecto: Carrito de compras en Kotlin para el Laboratorio 02 del curso de Programación Móvil Android. Se requiere refactorizar el código base tradicional a una arquitectura robusta basada en POO.

[TAREA]  
Refactoriza el programa del carrito de compras en Kotlin aplicando de forma explícita los 4 pilares de la Programación Orientada a Objetos (POO): Abstracción, Encapsulamiento, Herencia y Polimorfismo.

1. **Abstracción:** Crea una clase base abstracta `ProductoBase` con las propiedades esenciales y la firma del método abstracto `calcularSubtotal()`.
2. **Encapsulamiento:** Oculta los atributos internos (`private`) y expón su lectura de manera controlada mediante métodos *getter*.
3. **Herencia:** Define la subclase `ProductoFisico` que extienda de `ProductoBase`.
4. **Polimorfismo:** Sobrescribe (`override`) el método `calcularSubtotal()` en la subclase y centraliza la lógica en la clase gestora `CarritoDeCompras`.

[RESTRICCIONES]
* Kotlin idiomático y código limpio.
* Formateo del reporte en columnas alineadas utilizando `String.format`.
* Implementación de cálculo de IGV (18%), descuentos por rangos con `when` y búsqueda del producto de mayor precio con `maxByOrNull`.

---

## Estructura del Prompt
* **Rol:** Definición del perfil experto en Kotlin y docente de POO.
* **Contexto:** Identificación de la actividad de laboratorio y objetivo de migración.
* **Instrucción / Tarea:** Especificación técnica de cada pilar de POO a implementar.
* **Restricciones:** Reglas de formateo, uso de funciones de orden superior y estándares del lenguaje.

---

## Demostración de los 4 Pilares de la POO

* **Abstracción:** Clase abstracta `ProductoBase` que modela las características esenciales de un producto y declara `abstract fun calcularSubtotal(): Double`.
* **Encapsulamiento:** Definición de propiedades `private val nombre`, `private val precioBase` y la lista privada `productos` dentro de `CarritoDeCompras`, protegiendo el estado directo del objeto.
* **Herencia:** La clase `ProductoFisico` hereda (`:`) de `ProductoBase`, reutilizando su lógica base e integrando la propiedad `cantidad`.
* **Polimorfismo:** Implementación concreta mediante `override fun calcularSubtotal()` en `ProductoFisico`, permitiendo que la clase `CarritoDeCompras` procese colecciones de productos sin acoplarse a un tipo específico.