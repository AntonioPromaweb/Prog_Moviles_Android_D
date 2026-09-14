# Laboratorio 03 & Tarea - Mejora con Inteligencia Artificial

**Estudiante:** Antonio  
**Curso:** Desarrollo de Aplicaciones Móviles  
**Rama:** `mejora-ia`

---

## 1. Registro de Prompts y Decisiones (Mejora con IA)

| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
| :--- | :--- | :--- |
| **Prompt 1:** "Agrega validaciones para que los campos de cantidad y precio no acepten valores negativos o vacíos en Compose. Agrega también un botón LIMPIAR que restablezca el formulario." | Generó la estructura de validación lógica utilizando estados `remember` para capturar errores visuales e incluyó la función para el botón de reseteo. | **Aceptado con corrección:** Se conservó la lógica de validación y limpieza, pero se modificó manualmente el texto del botón principal de "GUARDAR" a "AGREGAR PRODUCTO" para respetar el diseño original de la guía. |
| **Prompt 2:** "Genera la pantalla completa para Registro de Notas usando Slider (0-20), Switch (redondeo) y Checkbox (confirmación) con un condicional when para evaluar el promedio." | La pantalla completa en Jetpack Compose (`TareaActivity.kt`) implementando Sliders interactivos, Switch de redondeo, Checkbox de confirmación y evaluación por rangos (`EXCELENTE`, `APROBADO`, `EN RECUPERACIÓN`, `DESAPROBADO`). | **Aceptado con refactorización:** Se aceptó toda la interfaz y la lógica de cálculo, pero se reubicó manualmente el archivo desde el paquete `ui.theme` hacia el paquete raíz (`com.example.laboratorio03`) para corregir errores de importación y mantener la estructura limpia. |
| **Prompt 3:** "Configura el AndroidManifest.xml para definir TareaActivity como la actividad principal (LAUNCHER)." | El código XML completo del manifest estableciendo el `<intent-filter>` con la acción `MAIN` y categoría `LAUNCHER` dentro de `TareaActivity`. | **Aceptado en su totalidad:** Se aplicó directamente al archivo `AndroidManifest.xml` sin modificaciones para ejecutar la Tarea directamente en el emulador. |

---

## 2. Reflexión sobre el trabajo con Asistentes IA

* **Eficiencia en Boilerplate:** La IA redujo drásticamente el tiempo de desarrollo al estructurar componentes complejos de Jetpack Compose (`Slider`, `Switch`, `Checkbox`) y la lógica condicional (`when`) en pocos segundos.
* **Necesidad de Control Humano:** Aunque la IA genera código funcional, no siempre respeta la arquitectura del proyecto (ubicación de paquetes) ni las restricciones específicas de interfaz (nombres de botones). La revisión y refactorización humana siguen siendo indispensables.