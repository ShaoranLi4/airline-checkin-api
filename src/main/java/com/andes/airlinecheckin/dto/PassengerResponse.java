package com.andes.airlinecheckin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassengerResponse {
    @JsonProperty("passengerId")
    private Integer passengerId;

    @JsonProperty("dni")
    private String dni;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("country")
    private String country;

    @JsonProperty("boardingPassId")
    private Integer boardingPassId;

    @JsonProperty("purchaseId")
    private Integer purchaseId;

    @JsonProperty("seatTypeId")
    private Integer seatTypeId;

    @JsonProperty("seatId")
    private Integer seatId;

    // Getters and setters
    public Integer getPassengerId() { return passengerId; }
    public void setPassengerId(Integer passengerId) { this.passengerId = passengerId; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getBoardingPassId() { return boardingPassId; }
    public void setBoardingPassId(Integer boardingPassId) { this.boardingPassId = boardingPassId; }

    public Integer getPurchaseId() { return purchaseId; }
    public void setPurchaseId(Integer purchaseId) { this.purchaseId = purchaseId; }

    public Integer getSeatTypeId() { return seatTypeId; }
    public void setSeatTypeId(Integer seatTypeId) { this.seatTypeId = seatTypeId; }

    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }
}
