package com.andes.airlinecheckin.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "flight") // Asegúrate que coincida con el nombre de la tabla
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id") // Esto mapea con la columna de la BD
    private Integer flightId;

    @Column(name = "takeoff_date_time") // Nombre exacto de la columna
    private Long takeoffDateTime;

    @Column(name = "takeoff_airport")
    private String takeoffAirport;

    @Column(name = "landing_date_time")
    private Long landingDateTime;

    @Column(name = "landing_airport")
    private String landingAirport;

    @Column(name = "airplane_id")
    private Integer airplaneId;

    // Constructores
    public Flight() {}

    public Flight(Integer flightId, Long takeoffDateTime, String takeoffAirport,
                  Long landingDateTime, String landingAirport, Integer airplaneId) {
        this.flightId = flightId;
        this.takeoffDateTime = takeoffDateTime;
        this.takeoffAirport = takeoffAirport;
        this.landingDateTime = landingDateTime;
        this.landingAirport = landingAirport;
        this.airplaneId = airplaneId;
    }

    // Getters y Setters (mantén las anotaciones @JsonProperty para JSON)
    @JsonProperty("flightId")
    public Integer getFlightId() { return flightId; }
    public void setFlightId(Integer flightId) { this.flightId = flightId; }

    @JsonProperty("takeoffDateTime")
    public Long getTakeoffDateTime() { return takeoffDateTime; }
    public void setTakeoffDateTime(Long takeoffDateTime) { this.takeoffDateTime = takeoffDateTime; }

    @JsonProperty("takeoffAirport")
    public String getTakeoffAirport() { return takeoffAirport; }
    public void setTakeoffAirport(String takeoffAirport) { this.takeoffAirport = takeoffAirport; }

    @JsonProperty("landingDateTime")
    public Long getLandingDateTime() { return landingDateTime; }
    public void setLandingDateTime(Long landingDateTime) { this.landingDateTime = landingDateTime; }

    @JsonProperty("landingAirport")
    public String getLandingAirport() { return landingAirport; }
    public void setLandingAirport(String landingAirport) { this.landingAirport = landingAirport; }

    @JsonProperty("airplaneId")
    public Integer getAirplaneId() { return airplaneId; }
    public void setAirplaneId(Integer airplaneId) { this.airplaneId = airplaneId; }

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", takeoffDateTime=" + takeoffDateTime +
                ", takeoffAirport='" + takeoffAirport + '\'' +
                ", landingDateTime=" + landingDateTime +
                ", landingAirport='" + landingAirport + '\'' +
                ", airplaneId=" + airplaneId +
                '}';
    }
}
