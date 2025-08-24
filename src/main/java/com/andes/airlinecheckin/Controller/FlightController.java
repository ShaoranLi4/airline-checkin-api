package com.andes.airlinecheckin.Controller;

import com.andes.airlinecheckin.dto.FlightResponse;
import com.andes.airlinecheckin.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/{id}/passengers")
    public FlightResponse getPassengers(@PathVariable("id") int flightId) {
        return flightService.getFlightWithPassengers(flightId);
    }
}