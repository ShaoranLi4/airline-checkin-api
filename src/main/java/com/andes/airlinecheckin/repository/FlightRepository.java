package com.andes.airlinecheckin.repository;

import com.andes.airlinecheckin.model.Flight;
import com.andes.airlinecheckin.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FlightRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para Flight - NOMBRES DE COLUMNAS
    private static final RowMapper<Flight> flightRowMapper = (rs, rowNum) -> {
        Flight flight = new Flight();
        flight.setFlightId(rs.getInt("flight_id"));
        flight.setTakeoffDateTime(rs.getLong("takeoff_date_time"));
        flight.setTakeoffAirport(rs.getString("takeoff_airport"));
        flight.setLandingDateTime(rs.getLong("landing_date_time"));
        flight.setLandingAirport(rs.getString("landing_airport"));
        flight.setAirplaneId(rs.getInt("airplane_id"));
        return flight;
    };

    // RowMapper para Passenger - MANEJO DE NULLS CORREGIDO
    private static final RowMapper<Passenger> passengerRowMapper = (rs, rowNum) -> {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(rs.getInt("passenger_id"));
        passenger.setDni(rs.getString("dni"));
        passenger.setName(rs.getString("name"));
        passenger.setAge(rs.getInt("age"));
        passenger.setCountry(rs.getString("country"));
        passenger.setBoardingPassId(rs.getInt("boarding_pass_id"));
        passenger.setPurchaseId(rs.getInt("purchase_id"));
        passenger.setSeatTypeId(rs.getInt("seat_type_id"));

        // Manejo correcto de valores NULL en seat_id
        int seatId = rs.getInt("seat_id");
        passenger.setSeatId(rs.wasNull() ? null : seatId);

        return passenger;
    };

    public Optional<Flight> findById(Integer flightId) {
        try {
            String sql = "SELECT * FROM flight WHERE flight_id = ?";
            Flight flight = jdbcTemplate.queryForObject(sql, flightRowMapper, flightId);
            return Optional.ofNullable(flight);
        } catch (Exception e) {
            // Log del error para debugging
            System.err.println("Error finding flight " + flightId + ": " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Passenger> findPassengersByFlightId(Integer flightId) {
        String sql = "SELECT p.passenger_id, p.dni, p.name, p.age, p.country, " +
                "bp.boarding_pass_id, bp.purchase_id, bp.seat_type_id, bp.seat_id " +
                "FROM boarding_pass bp " +
                "JOIN passenger p ON bp.passenger_id = p.passenger_id " +
                "WHERE bp.flight_id = ? ORDER BY bp.purchase_id";
        return jdbcTemplate.query(sql, passengerRowMapper, flightId);
    }

    public boolean existsById(Integer flightId) {
        try {
            String sql = "SELECT COUNT(*) FROM flight WHERE flight_id = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, flightId);
            return count != null && count > 0;
        } catch (Exception e) {
            System.err.println("Error checking if flight exists: " + e.getMessage());
            return false;
        }
    }

    public void updateSeatAssignment(Integer boardingPassId, Integer seatId, Integer flightId) {
        try {
            String sql = "UPDATE boarding_pass SET seat_id = ? " +
                    "WHERE boarding_pass_id = ? AND flight_id = ?";
            jdbcTemplate.update(sql, seatId, boardingPassId, flightId);
        } catch (Exception e) {
            System.err.println("Error updating seat assignment: " + e.getMessage());
            throw e;
        }
    }

    public List<Integer> findAvailableSeats(Integer airplaneId, Integer seatTypeId, Integer flightId, Integer limit) {
        try {
            String sql = "SELECT s.seat_id FROM seat s " +
                    "WHERE s.airplane_id = ? AND s.seat_type_id = ? " +
                    "AND s.seat_id NOT IN (" +
                    "   SELECT bp.seat_id FROM boarding_pass bp " +
                    "   WHERE bp.flight_id = ? AND bp.seat_id IS NOT NULL" +
                    ") " +
                    "ORDER BY s.seat_row, s.seat_column " +
                    "LIMIT ?";
            return jdbcTemplate.queryForList(sql, Integer.class, airplaneId, seatTypeId, flightId, limit);
        } catch (Exception e) {
            System.err.println("Error finding available seats: " + e.getMessage());
            throw e;
        }
    }

    // Método adicional para debugging
    public List<Flight> findAllFlights() {
        try {
            String sql = "SELECT * FROM flight";
            return jdbcTemplate.query(sql, flightRowMapper);
        } catch (Exception e) {
            System.err.println("Error finding all flights: " + e.getMessage());
            throw e;
        }
    }

    // Método adicional para verificar conexión
    public boolean testConnection() {
        try {
            String sql = "SELECT 1";
            jdbcTemplate.queryForObject(sql, Integer.class);
            return true;
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
