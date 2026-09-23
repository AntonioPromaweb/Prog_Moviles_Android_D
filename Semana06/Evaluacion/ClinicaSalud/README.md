# Clínica Salud+

**Luis Vasquez** - C24D

---

## Descripción

Aplicación Android desarrollada con Jetpack Compose que permite reservar citas médicas. Implementa navegación secuencial (Inicio → Perfil del médico → Agendar cita → Confirmación) y navegación secundaria mediante un menú lateral (Drawer).

---

## Requisitos Funcionales

- **RF-01:** La pantalla de Inicio debe mostrar un `LazyRow` con chips de especialidad (mínimo 2: Cardiología, Pediatría) y un `LazyColumn` con la lista de médicos (mínimo 3), cada tarjeta con nombre, especialidad y calificación.

- **RF-02:** La pantalla de Perfil del Médico debe recibir los datos del médico elegido por parámetro de navegación y mostrar un botón "Agendar cita".

- **RF-03:** La pantalla de Agendar Cita debe permitir la selección de fecha (mínimo 3 opciones) y hora (mínimo 3 opciones), ambas de selección única (tipo RadioButton).

- **RF-04:** La pantalla de Confirmación debe mostrar un resumen de la cita agendada (médico, fecha, hora) y un botón para volver al inicio.

- **RF-05:** El Menú Lateral (Drawer) debe abrirse desde el ícono ☰ en la `topBar` de Inicio y tener mínimo 3 destinos (Inicio, Mis citas, Historial médico).

- **RF-06:** La pantalla "Mis citas" debe mostrar un `LazyColumn` con las citas agendadas, cada una con su estado (Confirmada / Completada) diferenciado visualmente.

- **RF-07:** La navegación secuencial debe ser: Inicio → Perfil del médico → Agendar cita → Confirmación, con paso de parámetros del médico elegido.

---

## Estructura del Proyecto

com.tuapp.clinicasalud/
├── navigation/
│ ├── AppNavigation.kt (NavHost con las rutas)
│ └── Screen.kt (Sealed class con las rutas)
├── screens/
│ ├── HomeScreen.kt (Inicio con LazyRow y LazyColumn)
│ ├── DoctorProfileScreen.kt (Perfil del médico)
│ ├── ScheduleScreen.kt (Agendar cita)
│ ├── ConfirmationScreen.kt (Confirmación)
│ ├── MyAppointmentsScreen.kt (Mis citas)
│ └── MedicalHistoryScreen.kt (Historial médico)
├── ui.theme/ (Temas y colores)
└── MainActivity.kt (Punto de entrada)


---

## Tecnologías Utilizadas

- Kotlin
- Jetpack Compose
- Navigation Compose 2.7.7
- Material Design 3
- Android Studio

---

## Estado

Todo el estado de la aplicación se maneja con `remember` y `mutableStateOf`, **sin usar ViewModel ni MVVM**, tal como lo indica la consigna de la tarea.

---

## Cómo Ejecutar

1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle.
4. Ejecutar en un emulador o dispositivo físico (API 24+).

## Resultado Final

<img width="604" height="1211" alt="image" src="https://github.com/user-attachments/assets/c32f2985-ad04-4e21-b38b-492419988514" />
<img width="684" height="1459" alt="image" src="https://github.com/user-attachments/assets/27e3c1fc-dcca-43f0-ac83-15294a4582b8" />
<img width="738" height="1459" alt="image" src="https://github.com/user-attachments/assets/34920eb2-4d36-4485-8dfc-dee92799520b" />
<img width="695" height="1459" alt="image" src="https://github.com/user-attachments/assets/01ee046f-bcac-44f5-84d8-92f2abb44985" />
<img width="701" height="1459" alt="image" src="https://github.com/user-attachments/assets/d687e9d4-6bc4-43f5-8ddf-2ace1b9da61f" />
