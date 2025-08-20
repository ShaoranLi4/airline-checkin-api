package com.andes.airlinecheckin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{id}/passengers")
    public Map<String, Object> getPassengers(@PathVariable("id") int flightId) {
        Map<String, Object> response = new HashMap<>();

        try {
            // ===== Query para vuelo + pasajeros =====
            List<Map<String, Object>> results = jdbcTemplate.queryForList(
                    "SELECT  " +
                            "    f.flight_id        AS flightId, " +
                            "    f.takeoff_datetime AS takeoffDateTime, " +
                            "    f.landing_datetime AS landingDateTime, " +
                            "    f.takeoff_airport  AS takeoffAirport, " +
                            "    f.landing_airport  AS landingAirport, " +
                            "    f.airplane_id      AS airplaneId, " +
                            "    bp.boarding_pass_id, bp.purchase_id, bp.passenger_id, " +
                            "    p.dni, p.name, p.age, p.country, bp.seat_type_id, bp.seat_id " +
                            "FROM flight f " +
                            "LEFT JOIN boarding_pass bp ON f.flight_id = bp.flight_id " +
                            "LEFT JOIN passenger p ON bp.passenger_id = p.passenger_id " +
                            "WHERE f.flight_id = ?", flightId
            );

            if (results.isEmpty()) {
                response.put("code", 404);
                response.put("data", new HashMap<>());
                return response;
            }

            // Tomamos la info del vuelo (solo la primera fila)
            Map<String, Object> flightData = results.get(0);

            // Construir la lista de pasajeros
            List<Map<String, Object>> passengers = new ArrayList<>();
            for (Map<String, Object> row : results) {
                if (row.get("passenger_id") != null) {
                    Map<String, Object> passenger = new HashMap<>();
                    passenger.put("passengerId", row.get("passenger_id"));
                    passenger.put("dni", row.get("dni"));
                    passenger.put("name", row.get("name"));
                    passenger.put("age", row.get("age"));
                    passenger.put("country", row.get("country"));
                    passenger.put("boardingPassId", row.get("boarding_pass_id"));
                    passenger.put("purchaseId", row.get("purchase_id"));
                    passenger.put("seatTypeId", row.get("seat_type_id"));
                    passenger.put("seatId", row.get("seat_id"));
                    passengers.add(passenger);
                }
            }

            // Construir respuesta final
            Map<String, Object> data = new HashMap<>();
            data.put("flightId", flightData.get("flightId"));
            data.put("takeoffDateTime", ((Number) flightData.get("takeoffDateTime")).longValue());
            data.put("landingDateTime", ((Number) flightData.get("landingDateTime")).longValue());
            data.put("takeoffAirport", flightData.get("takeoffAirport"));
            data.put("landingAirport", flightData.get("landingAirport"));
            data.put("airplaneId", flightData.get("airplaneId"));
            data.put("passengers", passengers);

            response.put("code", 200);
            response.put("data", data);
            return response;

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            response.put("code", 404);
            response.put("data", new HashMap<>());
            return response;
        } catch (DataAccessException e) {
            response.put("code", 500);
            response.put("errors", "could not connect to db");
            return response;
        }
    }
}
