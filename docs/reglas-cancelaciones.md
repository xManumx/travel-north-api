# Reglas de Negocio - Cancelación de Reservas

| Regla | Qué evalúa | Si falla | Si pasa |
| :--- | :--- | :--- | :--- |
| **1. No cancelar dos veces** | Si el estado de la reserva ya es CANCELADA | Lanza ReglaNegocioException ; el manejador responde 400 | Sigue a la regla 2 |
| **2. Mínimo 3 días antes** | Calcula con ChronoUnit.DAYS los días entre hoy y fechaInicio del paquete; deben ser 3 o más | Lanza ReglaNegocioException con los días que faltan; 400 | Cambia el estado a CANCELADA y guarda. El cupo queda libre porque Manu solo suma reservas CONFIRMADA |
