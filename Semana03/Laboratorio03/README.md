# Laboratorio 03 - Registro de Productos en Jetpack Compose

**Estudiante:** Antonio  
**Curso:** Desarrollo de Aplicaciones Móviles

---

## Descripción del Proyecto
Aplicación móvil desarrollada en Android Studio con Jetpack Compose que permite el registro de nuevos productos mediante un formulario interactivo. La interfaz solicita el nombre, precio y cantidad del producto, calcula el importe total (`precio * cantidad`) y muestra una tarjeta con el resumen detallado del registro.

---

## Capturas de Pantalla

| Pantalla Vacía (Estado Inicial) | Producto Registrado |
| :---: | :---: |
| ![Estado Inicial](captura_inicial.png) | ![Producto Registrado](captura_registrado.png) |

---

## Pregunta del Laboratorio

### ¿Qué pasaría si declaras las variables de los campos SIN `remember`?

**Respuesta:**  
Si declaras las variables con `mutableStateOf("")` pero **sin el envoltorio `remember`**, la aplicación perderá el estado de los campos de texto cada vez que Compose realice una recomposición de la interfaz (por ejemplo, al presionar una tecla, escribir un carácter o interactuar con un botón).

**Explicación técnica:**
* `mutableStateOf` crea el estado reactivo que notifica a Compose que debe redibujar la pantalla cuando el valor cambia.
* `remember` le indica a Compose que debe **preservar y mantener guardado ese valor en memoria** a través de las diferentes recomposiciones.
* **Resultado de probarlo sin `remember`:** Cada vez que el usuario escribe una letra en el campo de texto, Compose detecta el cambio, vuelve a ejecutar la función `@Composable` de arriba a abajo y vuelve a inicializar la variable a su valor predeterminado (`""`). Esto provoca que el campo de texto se borre instantáneamente y sea imposible ingresar información.