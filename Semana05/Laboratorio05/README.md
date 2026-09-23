# Laboratorio 5: Navegación con Jetpack Compose (Versión con IA)

**Luis Vasquez** - C24D

---

## Descripción

Esta versión del Laboratorio 05 implementa un **Portal Académico** con un diseño moderno basado en Material Design 3, generado con ayuda del Agente de IA de Android Studio (Gemini). Se mantiene la misma funcionalidad de navegación que la versión sin IA, pero con una presentación visual mejorada.

---

## Requerimientos Funcionales

- **RF-01:** La aplicación debe iniciar en una pantalla de Login ("Portal Académico") con campos para correo institucional y contraseña, y un botón para acceder al sistema.

- **RF-02:** Al iniciar sesión, el usuario accede a una pantalla de bienvenida que muestra su nombre ("Bienvenido, Luis Vasquez") y dos tarjetas de acceso rápido: "Directorio de Alumnos" y "Mi Perfil Académico".

- **RF-03:** La pantalla "Directorio de Alumnos" debe mostrar una lista de 5 alumnos con su nombre, carrera y avatar, permitiendo seleccionar uno para ver su detalle.

- **RF-04:** La pantalla "Expediente Académico" debe recibir el identificador del alumno seleccionado como argumento tipado (Int) y mostrar su información detallada (ID, correo, facultad y biografía).

- **RF-05:** La pantalla "Configuración de Perfil" debe mostrar la información personal y académica de Luis Vasquez, con un botón para cerrar sesión que redirige al Login.

- **RF-06:** Todas las pantallas deben respetar la paleta de colores morados definida (`AcademicPurple`, `AcademicDarkPurple`, etc.) y usar íconos de Material Design, nunca emojis.

---

## Promt del Proyecto

Actúa como un desarrollador de Android con experiencia en diseño de interfaces usando Jetpack Compose y Material Design 3. Tengo una aplicación académica básica que necesita una renovación visual completa. Quiero que transformes su apariencia manteniendo intacta la funcionalidad y el flujo de navegación actual.
Estilo visual general: Usa una paleta basada en tonos morados suaves. Para elementos sólidos (botones, títulos, íconos) usa un morado medio como #7C6BAF. Para cabeceras con degradado, usa una transición de morado claro (#9575CD) a morado más profundo (#5E35B1). Evita fondos de pantalla completa demasiado oscuros; prioriza la claridad y el contraste.
Pantalla de Login - "Portal Académico":
Tarjeta blanca centrada con esquinas redondeadas (20.dp) sobre un fondo con degradado lila suave.
Título "Portal Académico" en morado oscuro, negrita, 26.sp, centrado.
Subtítulo "Accede a tu cuenta" en gris, 14.sp.
Campo "Correo Institucional" con ícono de sobre (Icons.Default.Email) a la izquierda, esquinas redondeadas.
Campo "Contraseña" con ícono de candado (Icons.Default.Lock) a la izquierda y un ícono de ojo (Icons.Default.Visibility / VisibilityOff) a la derecha para alternar la visibilidad. Usa el ícono estándar de Material, no emojis.
Botón "INICIAR SESIÓN" de ancho completo, fondo morado, texto blanco, esquinas redondeadas.
Enlace inferior "¿Olvidaste tu contraseña?" en morado, centrado.
Pantalla de Bienvenida - Home:
Fondo con degradado vertical de morado medio (arriba) a morado claro (abajo).
Todo el contenido debe estar centrado vertical y horizontalmente como un bloque compacto, con separación uniforme de 24-32.dp.
Saludo "Bienvenido, Luis Vasquez" en blanco, negrita, 32.sp.
Subtítulo "¿Qué deseas gestionar hoy?" en blanco semitransparente, 16.sp.
Tarjeta 1: "Directorio de Alumnos" con ícono de grupo (Icons.Default.People) dentro de un círculo, subtítulo "Ver y gestionar estudiantes".
Tarjeta 2: "Mi Perfil Académico" con ícono de persona (Icons.Default.Person), subtítulo "Datos personales y progreso".
Enlace inferior "Cerrar Sesión Segura" en rojo, con ícono de salida (Icons.AutoMirrored.Filled.Logout), anclado en la parte baja.
Pantalla de Lista - "Directorio de Alumnos":
TopAppBar con fondo blanco, flecha de retroceso morada, título "Directorio de Alumnos" en morado oscuro, negrita.
Lista con LazyColumn de tarjetas grises redondeadas.
Cada ítem: avatar circular con la inicial del alumno, nombre en negrita negro, carrera en morado, flecha Icons.AutoMirrored.Filled.KeyboardArrowRight a la derecha.
Alumnos: Juan León (Ingeniería de Sistemas), María García (Arquitectura), Carlos Pérez (Medicina), Ana López (Derecho), Luis Ramírez (Administración).
Al hacer clic, navega al detalle con el itemId.
Pantalla de Detalle - "Expediente Académico":
TopAppBar blanca con flecha de retroceso y título "Expediente Académico" en morado oscuro.
Cabecera grande con degradado morado medio a oscuro, esquinas inferiores redondeadas.
Avatar circular con borde blanco, superpuesto parcialmente entre la cabecera morada y el fondo blanco.
Nombre del alumno en negrita, carrera en morado.
Tarjeta gris con filas de datos: ID Estudiante, Correo Electrónico, Facultad (cada uno con su ícono).
Sección "Biografía" con un párrafo descriptivo.
Pantalla de Perfil - "Configuración de Perfil":
TopAppBar blanca o muy clara con flecha de retroceso y título "Configuración de Perfil" en morado oscuro.
Panel con degradado morado completo (sin división con blanco). El avatar circular debe estar totalmente contenido dentro de ese panel.
Nombre "Luis Vasquez" en blanco, negrita, 20.sp.
Sección "INFORMACIÓN PERSONAL": filas con ícono + etiqueta pequeña gris + valor en negrita:
Nombre Completo: Luis Vasquez
Correo: luis.vasquez@tecsup.edu.pe
Teléfono: +51 987 654 321
Sección "ACADÉMICO":
Facultad/Carrera: Ingeniería de Software
Campus: VI Ciclo
Botón "Cerrar Sesión" anclado abajo, con fondo rosa claro, texto rojo y ícono de salida.
Restricciones generales:
Tipografía Material 3 (Roboto), con jerarquía clara entre títulos, subtítulos y texto secundario.
Esquinas redondeadas en botones, tarjetas y campos.
Todos los íconos deben ser de Material Design, nunca emojis.
Ninguna TopAppBar debe ser completamente morada.
Ningún fondo de pantalla completa debe verse oscuro o pesado.
Mantén exactamente los mismos textos, campos y funcionalidad existentes; solo mejora el estilo visual.
Salida esperada: Código Kotlin completo con Jetpack Compose, listo para compilar, estructurado en los archivos correspondientes dentro de los paquetes navigation y screens.

## Resultado Final

