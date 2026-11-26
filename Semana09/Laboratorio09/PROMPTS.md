# PROMPTS.md — Fase 2 (con IA)

Rama: `mejora-ia-semana09` · Herramienta: Gemini en Android Studio
Alumno: Luis Antonio Vasquez Flores

En cada prompt le indiqué a Gemini las reglas del laboratorio (el ViewModel no usa Retrofit, `PostApi`,
`HttpException` ni `Context`; ninguna pantalla llama a la API; nada de `GlobalScope`, `catch` vacío ni `http://`).
Antes de aceptar sus cambios ("Keep all") leí el código, lo probé en el emulador y revisé con
`git diff --cached --stat` que solo cambiaran los archivos esperados.

## Tabla de prompts

| Prompt que escribí | Qué respondió Gemini | Qué cambié o descarté | Por qué |
|---|---|---|---|
| **1. Borrar publicación.** Le pedí `@DELETE("posts/{id}")` con `Response<Unit>` en `PostApi`, `borrar(id): Result<Unit>` en el Repository (revisando `isSuccessful`), `borrar(id)` en el ViewModel con un `StateFlow` de resultado, y un botón "Eliminar" con diálogo de confirmación en `PantallaDetalle`. | Modificó `PostApi`, `PostRepository`, `PostsViewModel`, `PantallaDetalle` y `MainActivity`. Agregó el `@DELETE`, un `borrar` en el Repository, el estado `borrado: UiState<Unit>?` y el diálogo de confirmación. Al borrar con éxito vuelve a la lista. | **Corregí** el Repository: Gemini repitió un bloque `try/catch` completo dentro de `borrar` en vez de reutilizar mi función privada `llamar { }`. Lo reemplacé por `llamar { ... if (!respuesta.isSuccessful) throw HttpException(respuesta) }` y actualicé el comentario, que decía "cinco funciones". **Acepté** el resto. | Código duplicado: tener un solo punto que captura las excepciones evita que una función nueva se olvide de relanzar `CancellationException`. La lógica de Gemini era correcta, pero mantenerla en un solo lugar es más fácil de explicar y de mantener. |
| **2. Búsqueda por título.** Le pedí un `StateFlow<String>` `busqueda` y `buscar(texto)` en el ViewModel que filtre por título (sin distinguir mayúsculas) las publicaciones ya descargadas, sin nueva llamada de red y combinada con el filtro por usuario; y un `OutlinedTextField` con botón para limpiar en `PantallaLista`. Le prohibí cambiar `PostApi` y `PostRepository`. | Modificó solo `PostsViewModel` y `PantallaLista`. Separó la lista descargada (`_listaOriginal`) de la lista visible, que construye con `combine(_listaOriginal, _busqueda)` y `stateIn(viewModelScope, WhileSubscribed(5000))`. Agregó el campo "Buscar por título" con botón ✕ y el texto "Sin publicaciones" cuando no hay resultados. | **Acepté sin cambios.** Solo comprobé que usara `viewModelScope` (no `GlobalScope`), que no importara Retrofit y que `cargar()` siguiera respetando el filtro por usuario. | La búsqueda trabaja sobre datos ya descargados, así que no genera tráfico de red. `combine` mantiene un único estado y la pantalla sigue usando solo el `when` sobre `UiState`. |
| **3. Encabezado con interceptor.** Le pedí un `Interceptor` de OkHttp que agregue `Accept: application/json` a todas las peticiones, agregado antes del `HttpLoggingInterceptor`, cambiando solo `RetrofitClient.kt`. | Agregó `headerInterceptor` con `chain.request().newBuilder().header("Accept", "application/json").build()` y `chain.proceed(...)`, y lo registró con `addInterceptor` antes del de logging. Solo cambió `RetrofitClient.kt` (9 líneas agregadas). | **Acepté sin cambios.** Verifiqué en Logcat que aparece `Accept: application/json` en la petición y que la URL base sigue siendo `https://`. | Va antes del interceptor de logging para que el encabezado se vea en Logcat. Usa `.header()` y no `.addHeader()` para reemplazar el valor en vez de duplicarlo. |
| **4. Diseño futurista.** Le pedí un tema oscuro fijo con estética de ciencia ficción espacial (colores neón, fondo de estrellas con `Canvas` sin imágenes ni dependencias, tipografía monoespaciada, esquinas recortadas), sin cambiar ningún texto ni tocar API, Repository ni ViewModel. | Cambió 8 archivos: `ui/theme/Color.kt`, `Theme.kt`, `Type.kt`, `Componentes.kt`, las tres pantallas y `MainActivity.kt`. Creó un `darkColorScheme` fijo, `FondoEstrellas()` con `Random(42L)`, `CutCornerShape` en tarjetas y botones, chips con borde neón y botón flotante amarillo. | **Corregí dos cosas** que Gemini no cubrió: (1) los iconos de la barra de estado (hora, batería) eran oscuros sobre fondo oscuro, y usé `enableEdgeToEdge` con `SystemBarStyle.dark`; (2) el "+" del botón flotante se veía blanco sobre amarillo, y le puse color oscuro explícito. **Acepté un cambio extra** que no pedí: `vm.limpiarBorrado()` dentro del `BackHandler`. | Los iconos del sistema no se ven si no se ajustan al tema oscuro. El `limpiarBorrado()` extra es correcto: evita que un resultado de borrado viejo reaparezca al volver con Atrás. No se cambió ningún texto visible ni la lógica. |

## Revisión de las reglas del laboratorio

| Regla | Resultado |
|---|---|
| `catch` vacío que traga excepciones | No apareció. El `catch` del Repository relanza `CancellationException` y convierte el resto en `Result.failure`. |
| Llamar a la API desde un composable | No apareció. Las pantallas solo llaman a funciones del ViewModel. |
| `GlobalScope` | No apareció. Todo usa `viewModelScope`. |
| URL con `http://` innecesario | No apareció. La URL base es `https://jsonplaceholder.typicode.com/`. |
| `HttpException` en el ViewModel | `PostsViewModel` no lo usa. Aparece solo en `UiState.kt` (por `mensajeDe`, que viene así en el proyecto base de la guía) y en el Repository, que es la capa de datos. |

## Lo que Gemini hizo mal o dejó incompleto

1. **Código duplicado en el Repository** (mejora 1): repitió el `try/catch` en vez de reutilizar `llamar { }`. Lo corregí.
2. **Comentario desactualizado** (mejora 1): decía "cinco funciones" cuando ya eran seis. Lo corregí.
3. **Accesibilidad del diseño** (mejora 4): no ajustó los iconos de la barra de estado a un fondo oscuro, y el "+" quedó con poco contraste. Lo corregí.
4. **Cambio no pedido** (mejora 4): agregó `vm.limpiarBorrado()` en el `BackHandler`. Lo acepté porque es correcto, pero lo registro porque no lo solicité.
