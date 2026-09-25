Actúa como un desarrollador senior de Android especializado en Jetpack Compose y Material Design 3. Necesito que generes el código UI completo y funcional para la aplicación **TECSUP Fit**, siguiendo las especificaciones visuales y funcionales que te detallo a continuación.

---

### REGLAS OBLIGATORIAS

1. **No inventes textos, campos ni pantallas.** Usa exactamente los textos indicados en cada sección.
2. **Usa íconos de Material Design** en todas las pantallas (`androidx.compose.material.icons.filled.*`). Está prohibido usar emojis.
3. **Genera el código completo**, sin comentarios de relleno como `// resto del código`. Todo debe compilar directamente.
4. **Toda pantalla secundaria debe tener una flecha de retroceso** (`Icons.AutoMirrored.Filled.ArrowBack`) en la parte superior para volver a la pantalla anterior, excepto las 4 pestañas principales del bottomBar.

---

### CONFIGURACIÓN DEL PROYECTO

- **Paquete base:** `com.tuapp.tecsupfit`
- **Paquetes internos:** `com.tuapp.tecsupfit.navigation` y `com.tuapp.tecsupfit.screens`
- **Navegación:** Usa `NavHost` con `rememberNavController()`.
- **Sin ViewModel ni MVVM:** Todo el estado se maneja con `remember` y `mutableStateOf`.

---

### PALETA DE COLORES OBLIGATORIA

- `VerdePrimary` = `Color(0xFF006B3F)`
- `VerdeLight` = `Color(0xFFE8F5E9)`
- `FondoClaro` = `Color(0xFFF9F9F9)`
- `CardFondo` = `Color(0xFFF1F3F5)`

---

### PANTALLAS A GENERAR

#### 1. `Screen.kt` (Rutas)

Define una `sealed class Screen` con las siguientes rutas:

- `Home` → `"home"`
- `ClassDetail` → `"classDetail/{classId}"` con función `createRoute(classId: Int)`
- `Confirmation` → `"confirmation/{classId}"` con función `createRoute(classId: Int)`
- `Reservations` → `"reservations"`
- `Routines` → `"routines"`
- `Profile` → `"profile"`

#### 2. `AppNavigation.kt` (NavHost)

- `startDestination = Screen.Home.route`
- Declara los `composable` para cada ruta, recibiendo el argumento `classId: Int`.

#### 3. `HomeScreen.kt` (Pantalla de Inicio)

- **TopAppBar verde** (`VerdePrimary`) con esquinas inferiores redondeadas que muestra:
    - Título: `"TECSUP Fit"` en blanco, negrita.
    - Subtítulo: `"Hola, Luis"` en blanco semitransparente, 12.sp.
- **LazyRow de filtros**: `"Hoy"` (seleccionado en verde oscuro) y `"Esta semana"` (en tono claro).
- **Texto "Clases disponibles"** en negrita.
- **LazyColumn de clases** con tarjetas (`CardFondo`) que contengan:
    - Ícono `Icons.Filled.FitnessCenter` en un cuadrado verde claro.
    - Nombre de la clase (ej. `"Yoga funcional"`) en negrita.
    - Horario y sala (ej. `"7:00 am · Sala 2"`) en verde.
- **Al hacer clic en una clase**, navega a `Screen.ClassDetail.createRoute(index)`.
- **BottomBar** con 4 pestañas: `"Inicio"` (activo), `"Reservas"`, `"Rutinas"`, `"Perfil"`.

#### 4. `ClassDetailScreen.kt` (Detalle de la clase)

- **TopAppBar** con flecha de retroceso y título `"Detalle de clase"`.
- **Tarjeta superior grande** con ícono `Icons.Filled.FitnessCenter` en fondo `VerdeLight`.
- **Nombre de la clase** (`"Cross Training"`) en negrita.
- **Horario** (`"6:00 pm · Sala 1 · 45 min"`) en verde.
- **Descripción**: `"Entrenamiento funcional de alta intensidad. Cupos limitados."`
- **Cupos disponibles**: `"8 de 12 cupos disponibles"`.
- **Botón inferior anclado** verde (`VerdePrimary`): `"Reservar cupo"`.

#### 5. `ConfirmationScreen.kt` (Confirmación)

- Ícono central `Icons.Filled.CheckCircle` en verde dentro de un círculo `VerdeLight`.
- Texto principal: `"¡Cupo reservado!"` en negrita.
- Subtítulos: `"Cross Training"` y `"Hoy, 6:00 pm · Sala 1"`.
- **Botón inferior verde**: `"Ver mis reservas"` que navega a `Screen.Reservations.route` limpiando la pila con `popUpTo`.

#### 6. `ReservationsScreen.kt` (Mis reservas)

- **TopAppBar verde** con título `"Mis reservas"`.
- **LazyColumn** con tarjetas de reservas:
    - **Reserva 1:** `"Cross Training"`, `"Hoy, 6:00 pm"`, barra lateral verde y etiqueta `"Confirmada"`.
    - **Reserva 2:** `"Yoga funcional"`, `"Ayer, 7:00 am"`, barra lateral gris y etiqueta `"Completada"`.
- **BottomBar** con la pestaña `"Reservas"` activa.

#### 7. `RoutinesScreen.kt` (Rutinas)

- **TopAppBar verde** con título `"Rutinas"`.
- **Contenido con tarjetas informativas** de rutinas asignadas al usuario (ej. "Lunes: Pecho y tríceps", "Martes: Espalda y bíceps", etc.).
- **BottomBar** con la pestaña `"Rutinas"` activa.

#### 8. `ProfileScreen.kt` (Mi perfil)

- **TopAppBar verde** con título `"Mi perfil"`.
- **Avatar circular grande** con iniciales `"LV"`.
- **Nombre:** `"Luis Vasquez"`.
- **Suscripción:** `"Plan Premium"` en verde.
- **Tarjetas de estadísticas** alineadas horizontalmente:
    - `"14 Clases"`.
    - `"3 Rachas"`.
- **BottomBar** con la pestaña `"Perfil"` activa.

---

### ENTREGABLE

Genera todo el código Kotlin completo, estructurado en archivos separados dentro de los paquetes `navigation` y `screens`, listo para ser copiado a Android Studio sin errores de compilación.