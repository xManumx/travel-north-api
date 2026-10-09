# Reglas de negocio: Reservas

Endpoint: `POST /api/paquetes/{paqueteId}/reservas`

| Regla | Qué evalúa | Si falla | Si pasa |
| --- | --- | --- | --- |
| 1. Paquete disponible | Que el paquete tenga `activo = true` y que su `fechaInicio` sea después de hoy | Lanza `ReglaNegocioException` y el manejador responde 400 | Sigue a la regla 2 |
| 2. Cupo disponible | Suma con `@Query` las personas de las reservas `CONFIRMADA` del paquete y la resta de `cupoMaximo`. Las personas pedidas deben ser 1 o más y caber en lo que queda | Lanza `ReglaNegocioException` con los cupos que quedan y responde 400 | Calcula `total` = precio del paquete por personas, pone estado `CONFIRMADA` y la fecha actual, guarda y responde 201 |

## Pruebas en Postman

| Caso | Petición | Body | Esperado |
| --- | --- | --- | --- |
| Exitoso | `POST /api/paquetes/1/reservas` | `{"nombreCliente":"Ana","emailCliente":"ana@mail.com","cantidadPersonas":3}` | 201 con `total` calculado |
| Falla regla 2 | `POST /api/paquetes/1/reservas` | Igual con `"cantidadPersonas":2` (paquete con cupo 4) | 400 |
| Falla regla 1 | `POST /api/paquetes/2/reservas` | Paquete desactivado | 400 |
| Consulta | `GET /api/reservas?email=ana@mail.com` | Ninguno | 200 |
