package com.andes.airlinecheckin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoardingPass {
    private Integer boardingPassId;
    private Integer purchaseId;
    private Integer passengerId;
    private Integer seatTypeId;
    private Integer seatId;
    private Integer flightId;

    // Constructores
    public BoardingPass() {}

    public BoardingPass(Integer boardingPassId, Integer purchaseId, Integer passengerId,
                        Integer seatTypeId, Integer seatId, Integer flightId) {
        this.boardingPassId = boardingPassId;
        this.purchaseId = purchaseId;
        this.passengerId = passengerId;
        this.seatTypeId = seatTypeId;
        this.seatId = seatId;
        this.flightId = flightId;
    }

    // Getters y Setters
    @JsonProperty("boardingPassId")
    public Integer getBoardingPassId() { return boardingPassId; }
    public void setBoardingPassId(Integer boardingPassId) { this.boardingPassId = boardingPassId; }

    @JsonProperty("purchaseId")
    public Integer getPurchaseId() { return purchaseId; }
    public void setPurchaseId(Integer purchaseId) { this.purchaseId = purchaseId; }

    @JsonProperty("passengerId")
    public Integer getPassengerId() { return passengerId; }
    public void setPassengerId(Integer passengerId) { this.passengerId = passengerId; }

    @JsonProperty("seatTypeId")
    public Integer getSeatTypeId() { return seatTypeId; }
    public void setSeatTypeId(Integer seatTypeId) { this.seatTypeId = seatTypeId; }

    @JsonProperty("seatId")
    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }

    @JsonProperty("flightId")
    public Integer getFlightId() { return flightId; }
    public void setFlightId(Integer flightId) { this.flightId = flightId; }

    @Override
    public String toString() {
        return "BoardingPass{" +
                "boardingPassId=" + boardingPassId +
                ", purchaseId=" + purchaseId +
                ", passengerId=" + passengerId +
                ", seatTypeId=" + seatTypeId +
                ", seatId=" + seatId +
                ", flightId=" + flightId +
                '}';
    }
}
