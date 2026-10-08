TECSUPstore
-----------


Prompt utilizado:

Quiero que te comportes como un desarrollador Android senior con amplia experiencia en Jetpack Compose y Material 3.

Actualmente tengo la base de una aplicación llamada "TECSUP Store" (paquete com.tuapp.navlab) en la rama main. La app ya cuenta con una lista de productos en tarjetas (TarjetaProducto.kt), un menú lateral (AppDrawer.kt), la coordinación de vistas (AppNavegacion.kt) y MainActivity.kt.

Mi objetivo es llevar a cabo la "Fase 2: Mejora con IA", la cual debe cumplir con los siguientes puntos obligatorios y funcionales:

1. Requerimiento principal (Badge dinámico):
- Vincular la acción de marcar o desmarcar un producto como favorito, desde el DropdownMenu de cada tarjeta, con un Badge numérico dinámico que se ubique en el ítem "Favoritos" del NavigationDrawer.
- Si la cantidad de favoritos es mayor a 0, el Badge debe mostrar dicho número con fondo morado (#5E2E8C) y texto en blanco.
- En la sección "Favoritos", solo deben aparecer los productos marcados. Si no hay ninguno, se debe mostrar un estado vacío con un mensaje informativo.

2. Funcionamiento completo del menú del producto (TarjetaProducto.kt):
- "Favoritos": Alternar el booleano isFavorite, cambiando el texto a "Quitar de favoritos" y el icono a rojo cuando esté seleccionado.
- "Compartir": Ejecutar un Intent nativo del sistema (Intent.ACTION_SEND) usando LocalContext para compartir el nombre y precio del producto con apps externas (WhatsApp, etc.).
- "Reportar": Mostrar un AlertDialog contextual para confirmar el reporte del producto y notificar mediante un Snackbar en la pantalla principal.
- Añadir un botón directo de compra (ShoppingCart) en la tarjeta para registrar órdenes en tiempo real.

3. Pantallas del Drawer y arquitectura modular (AppScreens.kt):
- Con el fin de no sobrecargar AppNavegacion.kt, desacoplar y crear el archivo AppScreens.kt con:
  a) LoginScreen: Formulario de inicio de sesión real (correo institucional y contraseña) para el usuario "Luis Vasquez" (luis.vasquez.f@tecsup.edu.pe). Si el usuario no está autenticado, la app debe bloquearse en esta pantalla.
  b) PerfilScreen: Vista con avatar de iniciales "LV", información académica de TECSUP (Diseño y Desarrollo de Software), tarjetas de estadísticas (cantidad de favoritos y pedidos) y botón funcional de "Cerrar sesión" que limpie la sesión y redirija al Login.
  c) MisPedidosScreen: Historial interactivo de compras realizadas desde el catálogo, mostrando código de orden, nombre, total y badge de estado ("Entregado" o "En preparación"). El Drawer también debe mostrar un badge con el total de pedidos acumulados.

4. Consistencia visual:
- Conservar la paleta morada institucional (#5E2E8C), esquinas redondeadas (RoundedCornerShape), avatares circulares y tipografía acorde a las especificaciones.

Por favor, proporcióname el código completo, modularizado y sin errores para cada uno de los archivos del proyecto.