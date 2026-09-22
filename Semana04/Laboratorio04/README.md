# Laboratorio 04: Mi Carrito TECSUP

**Estudiante:** [Vasquez Flores, Luis Antonio]  
**Curso:** Desarrollo de Aplicaciones Móviles

## Descripción del Proyecto
Aplicación desarrollada en Android Studio con Kotlin y Jetpack Compose. Permite gestionar un carrito de compras dinámico mediante un formulario de ingreso, renderizado reactivo de productos, cálculo automático de importes (Subtotal, IGV 18% y Total) y eliminación de ítems en tiempo real.

---

## Capturas de Pantalla

### 1. Estado Inicial (Carrito Vacío)

<img width="756" height="1459" alt="image" src="https://github.com/user-attachments/assets/aedc8708-192e-4d6d-9d46-8545ee2bbca6" />


### 2. Estado con Productos Agregados y Totales

<img width="731" height="1459" alt="image" src="https://github.com/user-attachments/assets/4267a364-035a-43c9-9387-6bfe9a79dcc9" />


---

## Respuestas a Preguntas Conceptuales

### a) ¿Por qué `mutableStateListOf` y no una `MutableList` normal?
Se utiliza `mutableStateListOf` porque es una colección observable integrada con el motor de Jetpack Compose. Cuando se agrega o elimina un elemento de esta lista, Compose detecta la modificación y dispara automáticamente la **recomposición** (actualización visual de la pantalla). Una `MutableList` normal cambiaría los datos en memoria, pero no notificaría a la interfaz de usuario para refrescar la lista ni los cálculos en tiempo real.

### b) ¿Por qué la lista es `val` si sus elementos cambian?
La variable se declara como `val` porque la **referencia al objeto de la lista en memoria nunca cambia**; sigue siendo la misma instancia creada por `remember { mutableStateListOf() }`. Lo que se modifica internamente es el contenido (los elementos) de la colección, no la referencia de la variable.

### c) ¿Qué hace `weight(1f)` en la `LazyColumn`?
El modificador `Modifier.weight(1f)` dentro de un contenedor `Column` le indica a la `LazyColumn` que debe **ocupar todo el espacio vertical disponible** que dejan libre el formulario (arriba) y el resumen de totales (abajo). Esto garantiza que la lista se pueda desplazar de manera independiente (scroll) sin empujar ni ocultar el formulario ni la tarjeta de totales fuera de la pantalla.
