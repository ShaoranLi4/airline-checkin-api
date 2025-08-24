package com.andes.airlinecheckin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    private Integer seatId;
    private String seatColumn;
    private Integer seatRow;
    private Integer seatTypeId;
    private Integer airplaneId;

    // Constructores
    public Seat() {}

    public Seat(Integer seatId, String seatColumn, Integer seatRow,
                Integer seatTypeId, Integer airplaneId) {
        this.seatId = seatId;
        this.seatColumn = seatColumn;
        this.seatRow = seatRow;
        this.seatTypeId = seatTypeId;
        this.airplaneId = airplaneId;
    }

    // Getters y Setters
    @JsonProperty("seatId")
    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }

    @JsonProperty("seatColumn")
    public String getSeatColumn() { return seatColumn; }
    public void setSeatColumn(String seatColumn) { this.seatColumn = seatColumn; }

    @JsonProperty("seatRow")
    public Integer getSeatRow() { return seatRow; }
    public void setSeatRow(Integer seatRow) { this.seatRow = seatRow; }

    @JsonProperty("seatTypeId")
    public Integer getSeatTypeId() { return seatTypeId; }
    public void setSeatTypeId(Integer seatTypeId) { this.seatTypeId = seatTypeId; }

    @JsonProperty("airplaneId")
    public Integer getAirplaneId() { return airplaneId; }
    public void setAirplaneId(Integer airplaneId) { this.airplaneId = airplaneId; }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", seatColumn='" + seatColumn + '\'' +
                ", seatRow=" + seatRow +
                ", seatTypeId=" + seatTypeId +
                ", airplaneId=" + airplaneId +
                '}';
    }
}
