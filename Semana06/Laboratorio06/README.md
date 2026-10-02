# Laboratorio 5: Navegación con Jetpack Compose

**Luis Vasquez** - C24D

---

## Requerimientos Funcionales

- **RF-01:** La aplicación debe permitir la navegación entre las pantallas de Inicio (Home), Lista (List), Detalle (Detail) y Perfil (Profile) utilizando Jetpack Navigation Compose.

- **RF-02:** La pantalla de Inicio debe ofrecer accesos directos a la lista de elementos y al perfil del usuario mediante botones interactivos.

- **RF-03:** La pantalla de Lista debe mostrar un conjunto de elementos (8 en total) y permitir al usuario seleccionar cualquiera de ellos para visualizar su información detallada.

- **RF-04:** La pantalla de Detalle debe recibir el identificador del elemento seleccionado como un argumento tipado (Int) a través del NavHost y mostrarlo en pantalla dentro de un componente Card.

- **RF-05:** El sistema debe permitir regresar a la pantalla anterior mediante el botón de retroceso (popBackStack) y, desde el perfil, volver al inicio limpiando el historial de navegación.

