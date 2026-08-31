# Sistema de Gestión de Estacionamiento en Kotlin

Este proyecto implementa un sistema de control de estacionamiento en consola utilizando Kotlin. La aplicación calcula tarifas según el tipo de vehículo, aplica recargos por la cantidad de horas estacionado y otorga un descuento para clientes frecuentes, finalizando con la emisión de una boleta detallada.

---

## Prompts Utilizados y Desarrollo por Fases

### 🔹 Prompt 1: Definición de la Clase Vehículo y Validaciones
**Prompt:**
> Crea la clase Vehículo en Kotlin para un sistema de estacionamiento. La clase debe manejar solo tres tipos de vehículos: Moto, Auto y Camioneta. Implementa los atributos: placa (String), tipo (String: Moto, Auto, Camioneta), horas (Entero, con validación para que no sea menor a 1), es_cliente_frecuente (Booleano) y nombre_cliente (String). Incluye el constructor que evite un registro con menos de 1 hora. Todo esto es una aplicación de terminal, no uses interfaces gráficas.

---

### 🔹 Prompt 2: Lógica de Negocio y Cálculo de Tarifas
**Prompt:**
> Extiende la clase Vehículo agregando la lógica de negocio para calcular el costo total del estacionamiento según estas reglas:
> - Tarifas base por hora: Moto (S/ 2.00), Auto (S/ 4.00), Camioneta (S/ 10.00).
> - Tarifas por horas: 1-2 Horas (0% de recargo), 3-5 Horas (20% de recargo por hora), 6ta Hora en adelante (50% de recargo por hora adicional).
> - Descuento por Frecuencia: Si es_cliente_frecuente es true, aplica un 10% de descuento sobre el monto total final.
> - Implementa el método calcular_total() detallando el monto por cada hora transcurrida.

---

### 🔹 Prompt 3: Generación de Boleta Detallada
**Prompt:**
> Implementa un método generar_boleta() en la clase Vehículo que imprima en consola un comprobante detallado con el siguiente formato:
> - Datos: Nombre del cliente, placa y tipo de vehículo.
> - Tabla de Tarifa por hora: Muestra la columna Hora, Tarifa Base, %Recargo e Importe por hora.
> - Resumen de pago: Subtotal, descuento aplicado y Monto Total a pagar.

---

## Requisitos de Ejecución

- **Lenguaje:** Kotlin 1.9+
- **Entorno de Desarrollo:** Android Studio / IntelliJ IDEA
- **JDK:** Java 17 o superior

<img width="1919" height="1018" alt="image" src="https://github.com/user-attachments/assets/dc5c9eb4-79a9-4913-91eb-f322f7804464" />
<img width="523" height="571" alt="image" src="https://github.com/user-attachments/assets/6cf5fe6b-50cb-4bf6-b637-a67106d813f0" />
