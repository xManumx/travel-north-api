# travel-north-api

API REST con Spring Boot y Spring Data JPA para una agencia de viajes: destinos, paquetes turisticos y reservas.

## Como correrlo

1. Copiar `.env.template` como `.env` y poner los datos de la base PostgreSQL (Neon o Supabase).
2. Compilar: `./mvnw clean compile`
3. Correr: `./mvnw spring-boot:run`

## Capas

- `model`: entidades JPA
- `repository`: interfaces de Spring Data
- `service`: reglas de negocio
- `controller`: endpoints REST
- `exception`: excepciones y manejador global
