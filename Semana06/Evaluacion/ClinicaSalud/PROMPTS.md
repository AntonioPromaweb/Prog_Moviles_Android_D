Actúa como un desarrollador senior de Android especializado en Jetpack Compose y Material Design 3. Necesito que generes el código UI completo y funcional para la aplicación **Clínica Salud+**, siguiendo las especificaciones visuales y funcionales que te detallo a continuación.

---

### REGLAS OBLIGATORIAS

1. **No inventes textos, campos ni pantallas.** Usa exactamente los textos indicados en cada sección.
2. **Usa íconos de Material Design** en todas las pantallas (`androidx.compose.material.icons.filled.*`). Está prohibido usar emojis.
3. **Genera el código completo**, sin comentarios de relleno como `// resto del código`. Todo debe compilar directamente.
4. **Toda pantalla de detalle debe tener una flecha de retroceso** (`Icons.AutoMirrored.Filled.ArrowBack`) en la parte superior para volver a la pantalla anterior.

---

### CONFIGURACIÓN DEL PROYECTO

- **Paquete base:** `com.tuapp.clinicasalud`
- **Paquetes internos:** `com.tuapp.clinicasalud.navigation` y `com.tuapp.clinicasalud.screens`
- **Navegación:** Usa `NavHost` con `rememberNavController()`.
- **Sin ViewModel ni MVVM:** Todo el estado se maneja con `remember` y `mutableStateOf`.

---

### PALETA DE COLORES OBLIGATORIA

- `MoradoPrincipal` = `Color(0xFF512DA8)`
- `FondoClaro` = `Color(0xFFF8F9FA)`
- `CardFondo` = `Color(0xFFF4F1F8)`
- `VerdeConfirmacion` = `Color(0xFFE8F5E9)`
- `VerdeTexto` = `Color(0xFF2E7D32)`

---

### PANTALLAS A GENERAR

#### 1. `Screen.kt` (Rutas)

Define una `sealed class Screen` con las siguientes rutas:

- `Home` → `"home"`
- `DoctorProfile` → `"doctorProfile/{doctorId}"` con función `createRoute(doctorId: Int)`
- `Schedule` → `"schedule/{doctorId}"` con función `createRoute(doctorId: Int)`
- `Confirmation` → `"confirmation/{doctorId}/{date}/{time}"` con función `createRoute(doctorId: Int, date: String, time: String)`
- `MyAppointments` → `"myAppointments"`
- `MedicalHistory` → `"medicalHistory"`
- `Profile` → `"profile"`

#### 2. `AppNavigation.kt` (NavHost)

- `startDestination = Screen.Home.route`
- Declara los `composable` para cada ruta, recibiendo los argumentos correspondientes (`doctorId: Int`, `date: String`, `time: String`).

#### 3. `HomeScreen.kt` (Pantalla de Inicio)

- **TopAppBar morada** (`MoradoPrincipal`) con esquinas inferiores redondeadas que muestra:
    - Título: `"Clínica Salud+"` en blanco, negrita.
    - Subtítulo: `"Hola, Juan"` en blanco semitransparente, 12.sp.
    - Ícono de menú (`Icons.Filled.Menu`) a la izquierda, blanco.
- **LazyRow de chips** con las especialidades: `"Cardiología"` (seleccionado en morado oscuro) y `"Pediatría"` (en tono claro).
- **Texto "Médicos disponibles"** en negrita.
- **LazyColumn de médicos** con tarjetas (`CardFondo`) que contengan:
    - Avatar circular con ícono `Icons.Filled.Add` en morado.
    - Nombre del médico (ej. `"Dra. Ana Torres"`) en negrita.
    - Especialidad (ej. `"Cardióloga"`) en morado.
    - Calificación a la derecha con `Icons.Filled.Star` y puntaje (ej. `"★ 4.9"`).
- **Al hacer clic en un médico**, navega a `Screen.DoctorProfile.createRoute(index)`.
- **Drawer (ModalNavigationDrawer)** envolviendo el Scaffold con:
    - Cabecera con círculo morado con iniciales `"JP"`, nombre `"Juan Pérez"`, subtítulo `"Paciente"`.
    - Línea divisoria horizontal.
    - Opciones: `"Inicio"`, `"Mis citas"`, `"Historial médico"`, `"Perfil"`, con íconos y resaltando la opción activa.

#### 4. `DoctorProfileScreen.kt` (Perfil del médico)

- **TopAppBar** con flecha de retroceso y título `"Perfil del médico"`.
- Avatar circular grande con ícono `Icons.Filled.Add` centrado.
- Nombre del médico en negrita (`"Dra. Ana Torres"`).
- Subtítulo: `"Cardióloga · 12 años exp."` en morado.
- Calificación: `"★ 4.9 (128 reseñas)"` con estrella amarilla.
- Párrafo descriptivo: `"Especialista en arritmias e hipertensión, formación en la Clínica Mayo."`
- **Botón inferior anclado** de color morado: `"Agendar cita"`.

#### 5. `ScheduleScreen.kt` (Agendar cita)

- **TopAppBar** con flecha de retroceso y título `"Agendar cita"`.
- Sección `"Selecciona fecha"` con chips horizontales: `"Jue 26"`, `"Vie 27"` (seleccionado en morado), `"Sáb 28"`.
- Sección `"Selecciona hora"` con chips: `"9:00"`, `"10:30"` (seleccionado), `"3:00"`.
- **Botón inferior morado** de confirmación: `"Confirmar cita"`.
- Al confirmar, navega a `Screen.Confirmation.createRoute(doctorId, selectedDate, selectedTime)`.

#### 6. `ConfirmationScreen.kt` (Confirmación)

- Ícono central `Icons.Filled.CheckCircle` en verde dentro de un círculo verde claro.
- Texto principal: `"¡Cita agendada!"` en negrita.
- Subtítulos: `"Dra. Ana Torres"` y `"Viernes 27, 10:30 am"`.
- **Botón inferior morado**: `"Ver mis citas"` que navega a `Screen.MyAppointments.route` limpiando la pila con `popUpTo`.

#### 7. `MyAppointmentsScreen.kt` (Mis citas)

- **TopAppBar** con flecha de retroceso y título `"Mis citas"`.
- **LazyColumn** con tarjetas de citas:
    - **Cita 1:** `"Dra. Ana Torres"`, `"Viernes 27, 10:30 am"`, etiqueta verde `"Confirmada"`.
    - **Cita 2:** `"Dr. Luis Vega"`, `"Miércoles 15, 3:00 pm"`, etiqueta gris `"Completada"`.

#### 8. `MedicalHistoryScreen.kt` (Historial médico)

- **TopAppBar** con flecha de retroceso y título `"Historial médico"`.
- **LazyColumn** con tarjetas:
    - **Tarjeta 1:** `"Consulta General - 12 Enero 2026"`. Detalle: `"Chequeo de rutina. Presión arterial normal. Se recetan vitaminas."`
    - **Tarjeta 2:** `"Cardiología - 05 Noviembre 2025"`. Detalle: `"Electrocardiograma sin anomalías. Próxima revisión en 1 año."`

#### 9. `ProfileScreen.kt` (Mi perfil)

- **TopAppBar** con flecha de retroceso y título `"Mi perfil"`.
- Avatar circular grande con iniciales `"JP"`.
- Nombre: `"Juan Pérez"`.
- Correo: `"juan.perez@tecsup.edu.pe"`.
- **Secciones en tarjetas con íconos:**
    - **Información Personal:** Teléfono (`"+51 987 654 321"`), Dirección (`"Av. Lima 123, Lima"`).
    - **Datos Médicos:** Seguro (`"Pacífico Seguros"`), Tipo de Sangre (`"O+"`).
- **Botón inferior** morado o rojo para `"Cerrar sesión"`.

---

### ENTREGABLE

Genera todo el código Kotlin completo, estructurado en archivos separados dentro de sus respectivos paquetes (`navigation` y `screens`), listo para ser copiado a Android Studio sin errores de compilación.