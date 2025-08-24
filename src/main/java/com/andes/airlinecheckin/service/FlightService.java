package com.andes.airlinecheckin.service;

import com.andes.airlinecheckin.dto.FlightData;
import com.andes.airlinecheckin.dto.FlightResponse;
import com.andes.airlinecheckin.dto.PassengerResponse;
import com.andes.airlinecheckin.model.Flight;
import com.andes.airlinecheckin.model.Passenger;
import com.andes.airlinecheckin.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatAssignmentService seatAssignmentService;

    @Autowired
    private JdbcTemplate jdbcTemplate; // Inyectado automáticamente por Spring Boot

    public FlightResponse getFlightWithPassengers(int flightId) {
        try {
            // Primero asignar asientos automáticamente
            seatAssignmentService.assignSeats(flightId);

            // Verificar si el vuelo existe
            if (!flightRepository.existsById(flightId)) {
                return createNotFoundResponse();
            }

            // Obtener información del vuelo
            Optional<Flight> flightOpt = flightRepository.findById(flightId);
            if (flightOpt.isEmpty()) {
                return createNotFoundResponse();
            }

            Flight flight = flightOpt.get();

            // Obtener pasajeros con sus boarding passes
            List<Passenger> passengers = flightRepository.findPassengersByFlightId(flightId);

            // Construir FlightData
            FlightData flightData = createFlightData(flight, passengers);

            return createSuccessResponse(flightData);

        } catch (EmptyResultDataAccessException e) {
            return createNotFoundResponse();
        } catch (Exception e) {
            e.printStackTrace(); // Para debugging
            return createErrorResponse("could not connect to db: " + e.getMessage());
        }
    }

    // Los demás métodos helper permanecen igual...
    private FlightData createFlightData(Flight flight, List<Passenger> passengers) {
        FlightData flightData = new FlightData();
        flightData.setFlightId(flight.getFlightId());
        flightData.setTakeoffDateTime(flight.getTakeoffDateTime());
        flightData.setTakeoffAirport(flight.getTakeoffAirport());
        flightData.setLandingDateTime(flight.getLandingDateTime());
        flightData.setLandingAirport(flight.getLandingAirport());
        flightData.setAirplaneId(flight.getAirplaneId());
        flightData.setPassengers(convertToPassengerResponseList(passengers));
        return flightData;
    }

    private List<PassengerResponse> convertToPassengerResponseList(List<Passenger> passengers) {
        List<PassengerResponse> passengerResponses = new ArrayList<>();

        for (Passenger passenger : passengers) {
            PassengerResponse response = new PassengerResponse();
            response.setPassengerId(passenger.getPassengerId());
            response.setDni(passenger.getDni());
            response.setName(passenger.getName());
            response.setAge(passenger.getAge());
            response.setCountry(passenger.getCountry());
            response.setBoardingPassId(passenger.getBoardingPassId());
            response.setPurchaseId(passenger.getPurchaseId());
            response.setSeatTypeId(passenger.getSeatTypeId());
            response.setSeatId(passenger.getSeatId());
            passengerResponses.add(response);
        }

        return passengerResponses;
    }

    private FlightResponse createSuccessResponse(FlightData flightData) {
        return new FlightResponse(200, flightData);
    }

    private FlightResponse createNotFoundResponse() {
        FlightResponse response = new FlightResponse();
        response.setCode(404);
        response.setData(null);
        return response;
    }

    private FlightResponse createErrorResponse(String errorMessage) {
        FlightResponse response = new FlightResponse();
        response.setCode(400);
        response.setErrors(errorMessage);
        return response;
    }
}
