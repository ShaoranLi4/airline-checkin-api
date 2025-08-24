package com.andes.airlinecheckin.service;

import com.andes.airlinecheckin.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SeatAssignmentService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FlightRepository flightRepository;

    @Transactional
    public void assignSeats(int flightId) {
        try {
            // Obtener boarding passes sin asiento asignado usando repository
            List<Map<String, Object>> unassignedPassengers = jdbcTemplate.queryForList(
                    "SELECT bp.boarding_pass_id, bp.purchase_id, bp.seat_type_id, " +
                            "p.passenger_id, p.age, p.name " +
                            "FROM boarding_pass bp " +
                            "JOIN passenger p ON bp.passenger_id = p.passenger_id " +
                            "WHERE bp.flight_id = ? AND bp.seat_id IS NULL", flightId);

            if (unassignedPassengers.isEmpty()) {
                return; // Todos ya tienen asiento
            }

            // Agrupar por purchase_id
            Map<Integer, List<Map<String, Object>>> groups = new HashMap<>();
            for (Map<String, Object> passenger : unassignedPassengers) {
                Integer purchaseId = (Integer) passenger.get("purchase_id");
                groups.computeIfAbsent(purchaseId, k -> new ArrayList<>()).add(passenger);
            }

            // Obtener información del avión usando repository
            Optional<com.andes.airlinecheckin.model.Flight> flightOpt = flightRepository.findById(flightId);
            if (flightOpt.isEmpty()) {
                throw new RuntimeException("Flight not found");
            }

            Integer airplaneId = flightOpt.get().getAirplaneId();

            // Asignar asientos a cada grupo
            for (List<Map<String, Object>> group : groups.values()) {
                assignSeatsToGroup(group, airplaneId, flightId);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error assigning seats: " + e.getMessage(), e);
        }
    }

    private void assignSeatsToGroup(List<Map<String, Object>> group, Integer airplaneId, int flightId) {
        // Verificar si hay menores y adultos en el grupo
        boolean hasAdults = group.stream().anyMatch(p -> (Integer) p.get("age") >= 18);
        boolean hasMinors = group.stream().anyMatch(p -> (Integer) p.get("age") < 18);

        if (hasMinors && !hasAdults) {
            throw new RuntimeException("Minor without adult accompaniment in purchase group");
        }

        // Obtener tipo de asiento común del grupo
        Integer seatTypeId = (Integer) group.get(0).get("seat_type_id");

        // Buscar asientos disponibles usando repository
        List<Integer> availableSeats = flightRepository.findAvailableSeats(
                airplaneId, seatTypeId, flightId, group.size());

        if (availableSeats.size() >= group.size()) {
            // Asignar asientos consecutivos al grupo
            for (int i = 0; i < group.size(); i++) {
                Map<String, Object> passenger = group.get(i);
                Integer seatId = availableSeats.get(i);

                flightRepository.updateSeatAssignment(
                        (Integer) passenger.get("boarding_pass_id"),
                        seatId,
                        flightId
                );
            }
        } else {
            // Asignar asientos individualmente si no hay suficientes consecutivos
            assignIndividualSeats(group, airplaneId, seatTypeId, flightId);
        }
    }

    private void assignIndividualSeats(List<Map<String, Object>> group, Integer airplaneId,
                                       Integer seatTypeId, int flightId) {
        for (Map<String, Object> passenger : group) {
            List<Integer> availableSeats = flightRepository.findAvailableSeats(
                    airplaneId, seatTypeId, flightId, 1);

            if (!availableSeats.isEmpty()) {
                flightRepository.updateSeatAssignment(
                        (Integer) passenger.get("boarding_pass_id"),
                        availableSeats.get(0),
                        flightId
                );
            }
        }
    }
}
