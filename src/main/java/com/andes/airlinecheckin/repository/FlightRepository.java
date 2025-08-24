package com.andes.airlinecheckin.repository;

import com.andes.airlinecheckin.model.Flight;
import com.andes.airlinecheckin.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FlightRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para Flight
    private static final RowMapper<Flight> flightRowMapper = (rs, rowNum) -> {
        Flight flight = new Flight();
        flight.setFlightId(rs.getInt("flight_id"));
        flight.setTakeoffDateTime(rs.getLong("takeoff_datetime"));
        flight.setTakeoffAirport(rs.getString("takeoff_airport"));
        flight.setLandingDateTime(rs.getLong("landing_datetime"));
        flight.setLandingAirport(rs.getString("landing_airport"));
        flight.setAirplaneId(rs.getInt("airplane_id"));
        return flight;
    };

    // RowMapper para Passenger
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
        passenger.setSeatId(rs.getInt("seat_id"));
        return passenger;
    };

    public Optional<Flight> findById(Integer flightId) {
        try {
            String sql = "SELECT * FROM flight WHERE flight_id = ?";
            Flight flight = jdbcTemplate.queryForObject(sql, flightRowMapper, flightId);
            return Optional.ofNullable(flight);
        } catch (Exception e) {
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
        String sql = "SELECT COUNT(*) FROM flight WHERE flight_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, flightId);
        return count != null && count > 0;
    }

    public void updateSeatAssignment(Integer boardingPassId, Integer seatId, Integer flightId) {
        String sql = "UPDATE boarding_pass SET seat_id = ? " +
                "WHERE boarding_pass_id = ? AND flight_id = ?";
        jdbcTemplate.update(sql, seatId, boardingPassId, flightId);
    }

    public List<Integer> findAvailableSeats(Integer airplaneId, Integer seatTypeId, Integer flightId, Integer limit) {
        String sql = "SELECT s.seat_id FROM seat s " +
                "WHERE s.airplane_id = ? AND s.seat_type_id = ? " +
                "AND s.seat_id NOT IN (" +
                "   SELECT bp.seat_id FROM boarding_pass bp " +
                "   WHERE bp.flight_id = ? AND bp.seat_id IS NOT NULL" +
                ") " +
                "ORDER BY s.seat_row, s.seat_column " +
                "LIMIT ?";
        return jdbcTemplate.queryForList(sql, Integer.class, airplaneId, seatTypeId, flightId, limit);
    }
}
