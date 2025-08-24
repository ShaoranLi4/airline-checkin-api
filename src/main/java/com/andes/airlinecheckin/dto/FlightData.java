package com.andes.airlinecheckin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FlightData {
    @JsonProperty("flightId")
    private Integer flightId;

    @JsonProperty("takeoffDateTime")
    private Long takeoffDateTime;

    @JsonProperty("takeoffAirport")
    private String takeoffAirport;

    @JsonProperty("landingDateTime")
    private Long landingDateTime;

    @JsonProperty("landingAirport")
    private String landingAirport;

    @JsonProperty("airplaneId")
    private Integer airplaneId;

    @JsonProperty("passengers")
    private List<PassengerResponse> passengers;

    // Getters and setters
    public Integer getFlightId() { return flightId; }
    public void setFlightId(Integer flightId) { this.flightId = flightId; }

    public Long getTakeoffDateTime() { return takeoffDateTime; }
    public void setTakeoffDateTime(Long takeoffDateTime) { this.takeoffDateTime = takeoffDateTime; }

    public String getTakeoffAirport() { return takeoffAirport; }
    public void setTakeoffAirport(String takeoffAirport) { this.takeoffAirport = takeoffAirport; }

    public Long getLandingDateTime() { return landingDateTime; }
    public void setLandingDateTime(Long landingDateTime) { this.landingDateTime = landingDateTime; }

    public String getLandingAirport() { return landingAirport; }
    public void setLandingAirport(String landingAirport) { this.landingAirport = landingAirport; }

    public Integer getAirplaneId() { return airplaneId; }
    public void setAirplaneId(Integer airplaneId) { this.airplaneId = airplaneId; }

    public List<PassengerResponse> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerResponse> passengers) { this.passengers = passengers; }
}
