# API Check-in Andes

Proyecto de prueba técnica para desarrollador/a web junior.  
Esta API permite consultar la información de un vuelo y la lista de pasajeros con sus respectivos asientos asignados, simulando un sistema de check-in de aerolínea.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- MySQL
- Maven
- JdbcTemplate

## Cómo ejecutar

1. Clonar el repositorio:

```bash
git clone https://github.com/TU_USUARIO/Airline.git
cd Airline

spring.datasource.url=jdbc:mysql://localhost:3306/tu_base
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

mvn spring-boot:run

GET /flights/{id}/passengers

{
  "code": 200,
  "data": {
    "flightId": 1,
    "takeoffDateTime": 1688207580,
    "takeoffAirport": "Aeropuerto Internacional Arturo Merino Benitez, Chile",
    "landingDateTime": 1688221980,
    "landingAirport": "Aeropuerto Internacional Jorge Cháve, Perú",
    "airplaneId": 1,
    "passengers": [
      {
        "passengerId": 90,
        "dni": 983834822,
        "name": "Marisol",
        "age": 44,
        "country": "México",
        "boardingPassId": 24,
        "purchaseId": 47,
        "seatTypeId": 1,
        "seatId": 1
      }
    ]
  }
}

{ "code": 404, "data": {} }


{ "code": 500, "errors": "could not connect to db" }
