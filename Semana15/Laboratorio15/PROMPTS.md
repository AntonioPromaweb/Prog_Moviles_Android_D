# PROMPTS.md · Fase 2 (con IA)

**Laboratorio 15 · Notificaciones** — AvisosCampus
**Alumno:** Luis Antonio Vasquez Flores · **Herramienta:** Gemini en Android Studio (agente)
**Rama:** `mejora-ia-semana15`

La IA propone y yo decido: cada respuesta se revisó contra las reglas de la guía (nada de `Context`,
`NotificationManager` ni `BroadcastReceiver` en el ViewModel, el Repository sin `Context`, y todo
receptor se quita en `onDispose` o está declarado de forma segura) y se probó en el emulador antes
de aceptarla. Cada mejora tiene su propio commit.

## Mejoras y commits

| # | Mejora | Capas que cambia | Commit |
|---|---|---|---|
| 1 | Texto largo con `BigTextStyle` | Notificador | `4a27c7a` · feat(notificacion): muestra el texto completo con BigTextStyle |
| 2 | Historial persistente con DataStore | **Repository + ViewModel** + MainActivity | `42ed90b` · feat(data): guarda el historial con DataStore para que no se pierda |
| 3 | Botón «Posponer 10 s» en la notificación | Notificador + Receiver + **ViewModel** | `80d05dc` · feat(notificacion): agrega accion Posponer 10 s con BroadcastReceiver |
| 4 | Borrar historial (extra) | **Repository + ViewModel** + pantalla | `bb76268` · feat(historial): agrega boton para borrar el historial guardado |
| 5 | Diseño espacial con estrellas y botones animados | Pantalla (ui) | `2753a87` · feat(ui): aplica tema espacial con fondo de estrellas y botones animados |

## Bitácora de prompts

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| «Quiero que al expandir la notificación se vea el texto completo (`BigTextStyle`). Cambia SOLO `mostrar()` de `Notificador.kt` y mantén permiso, `PendingIntent`, `setAutoCancel`, id y canal.» | Agregó una sola línea: `.setStyle(NotificationCompat.BigTextStyle().bigText(aviso.texto))`. | Nada: lo acepté tal cual. | Cambio mínimo, no toca el ViewModel ni las reglas. Verifiqué que tocar la notificación sigue abriendo la app. |
| «Haz el historial persistente con DataStore Preferences. La interface pasa a `Flow` y `suspend registrar`; crea `AvisosRepositoryDataStore(dataStore)` con JSON de `org.json`, máximo 50 avisos, id = mayor id + 1; el DataStore se crea en `MainActivity`; el ViewModel usa `stateIn`. El Repository NO usa `Context`.» | Cambió la interface, creó `AvisosRepositoryDataStore` (recibe el `DataStore` por constructor), conservó `AvisosRepositoryMemoria`, creó el `DataStore` con la extensión de `Context` solo en `MainActivity` y usó `repo.avisos.stateIn(...)` en el ViewModel. | Acepté el código. Anoté tres observaciones: el `nuevoAviso!!` (es seguro porque `edit` siempre se ejecuta), no hay `try/catch` si el JSON guardado se corrompiera, y al abrir la app se ve un instante «Sin avisos todavía» hasta que DataStore carga. | El punto clave era que el Repository no tuviera `Context`: Gemini lo resolvió inyectando el `DataStore`. Comprobé que al cerrar la app del todo y reabrirla el historial se conserva. |
| «Agrega un botón «Posponer 10 s» solo a la notificación del Recordatorio: `addAction` con `PendingIntent.getBroadcast` (`FLAG_IMMUTABLE`, `requestCode = aviso.id`) hacia `PosponerReceiver`; el receptor cancela la notificación y avisa por un objeto con `SharedFlow`; el ViewModel lo escucha y llama a `recordarEn(10)`. Receptor declarado en el manifest.» | Creó `PosponerReceiver` (`onReceive` corto) y `PosponerEventos` (`SharedFlow` con `extraBufferCapacity = 1`), declaró el receptor con `exported="false"`, agregó la acción en `mostrar()` solo si el origen es `RECORDATORIO` y un bloque `init` en el ViewModel que colecta `PosponerEventos`. | Nada: lo acepté. Anoté una **limitación**: si Android cerró el proceso de la app, `PosponerEventos` no tiene quién lo escuche y «Posponer» solo cierra la notificación sin reprogramar. También anoté que el `init` quedó antes de otras propiedades (funciona porque el cuerpo solo corre al llegar un evento). | El ViewModel no usa `Context`, `NotificationManager` ni `BroadcastReceiver`: solo escucha un objeto simple. Para reprogramar con la app cerrada haría falta `AlarmManager` o `WorkManager`, fuera del alcance de la guía. Probé que al posponer llega un segundo Recordatorio a los 10 s. |
| «Agrega un botón «Borrar historial»: `suspend fun limpiar()` en el Repository (DataStore y memoria), `borrarHistorial()` en el ViewModel y un botón en `HistorialAvisos` con `AlertDialog` de confirmación, visible solo si hay avisos. `HistorialAvisos` no conoce el ViewModel.» | Implementó `limpiar()` (en DataStore quita la clave con `preferences.remove`), `borrarHistorial()` con `viewModelScope.launch`, el botón con diálogo y pasó `onBorrar` desde `PantallaAvisos`. | Nada: lo acepté. Anoté que el estado del diálogo usa `remember` (se cierra al rotar con el diálogo abierto) y que tras borrar los ids vuelven a empezar en 1, por lo que una notificación vieja con ese id podría ser reemplazada. | Es la consecuencia lógica de la persistencia. Probé cancelar, confirmar, y que al cerrar y reabrir la app el historial sigue vacío. |
| «Rediseño solo visual con ambiente de superhéroes (rojo, dorado y fondo oscuro), con una pequeña animación en cada botón. Sin logos ni nombres de marcas.» | Creó la paleta, el componente `BotonAnimado` (se encoge con spring al pulsar), tarjetas con esquinas cortadas, animación del número de la cuenta regresiva, color animado de la etiqueta del permiso y aparición con fundido en el historial. No tocó la lógica. | **Descarté el diseño completo** (no llegó a commitearse). Antes de descartarlo corregí dos cosas: el `AnimatedVisibility(visible = true)` nunca animaba (arranca visible y, sin clave por aviso, el efecto caería en la fila equivocada), y lo cambié a `key(aviso.id)` con `MutableTransitionState(false)`; y el título que escribió con espacio («AVISOS CAMPUS») lo volví a «AVISOSCAMPUS». | Técnicamente cumplía las reglas, pero visualmente no me convenció. Preferí un tema espacial. Las dos correcciones se conservaron en el diseño final. |
| «Reemplaza el diseño anterior por un tema espacial (galaxia, hiperespacio, cabina de nave), sin logos ni marcas: negro espacial, cian neón y amarillo; `FondoEstrellas()` con `Canvas` y titileo; mismos parámetros en `BotonAnimado` con borde de neón que brilla al pulsar; etiquetas de color. No cambies la lógica.» | Creó `FondoEstrellas()` (160 estrellas fijas con `Random(42)` y titileo con `rememberInfiniteTransition`), `Scaffold` transparente sobre el fondo, barra superior con título amarillo y línea cian, tarjetas con borde de neón, `BotonAnimado` con la misma firma y borde que brilla al pulsar. | Nada: lo acepté. Mantuvo mi corrección de `key(aviso.id)` en el historial. | Solo cambió archivos de `ui/` y `ui/theme/`; el Repository, el ViewModel, el Notificador y los receptores quedaron como estaban. La fase de la animación se lee dentro del dibujo del `Canvas`, así que no recompone toda la pantalla. |

## Revisión de reglas

| Regla | Resultado |
|---|---|
| El ViewModel no usa `Context`, `NotificationManager` ni `BroadcastReceiver` | Cumple: solo escucha el objeto `PosponerEventos` (un `SharedFlow`). El único `Context` mencionado es un comentario. |
| El Repository no usa `Context` | Cumple: `AvisosRepositoryDataStore` recibe el `DataStore` por constructor; el `Context` solo aparece en `MainActivity`. |
| Todo receptor registrado se quita en `onDispose` | Cumple: `ModoAvionReceiver` se registra en `DisposableEffect` y se quita en `onDispose`. `PosponerReceiver` está declarado en el manifest, con `exported="false"`, y su `onReceive` es corto. |
| `PendingIntent` inmutable | Cumple: los dos usan `FLAG_IMMUTABLE`. |
| Sin `GlobalScope` ni `catch` vacíos | Cumple. |
| Al menos una mejora cambia el Repository o el ViewModel | Cumple: las mejoras 2, 3 y 4. |

## Defectos y puntos a destacar

- **Animación que no animaba:** Gemini escribió `AnimatedVisibility(visible = true)`, que no reproduce la entrada si ya está visible desde el inicio, y sin clave por aviso la animación caía en la fila equivocada. Lo corregí con `key(aviso.id)` y un estado de transición que arranca en oculto.
- **Nombre alterado:** el título salió como «AVISOS CAMPUS»; lo devolví a «AVISOSCAMPUS», el nombre real de la app.
- **Limitación de «Posponer»:** depende de que el proceso de la app siga vivo. Es una decisión consciente de la mejora, documentada arriba.
- **Persistencia:** no hay manejo de JSON corrupto y puede verse un instante el historial vacío al abrir. No afecta al uso normal.
- La IA no rompió ninguna regla de oro de la guía en esta fase; aun así cada cambio se revisó y probó antes de commitear.
