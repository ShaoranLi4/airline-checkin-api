package com.andes.airlinecheckin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Passenger {
    private Integer passengerId;
    private String dni;
    private String name;
    private Integer age;
    private String country;
    private Integer boardingPassId;
    private Integer purchaseId;
    private Integer seatTypeId;
    private Integer seatId;

    // Constructores
    public Passenger() {}

    public Passenger(Integer passengerId, String dni, String name, Integer age,
                     String country, Integer boardingPassId, Integer purchaseId,
                     Integer seatTypeId, Integer seatId) {
        this.passengerId = passengerId;
        this.dni = dni;
        this.name = name;
        this.age = age;
        this.country = country;
        this.boardingPassId = boardingPassId;
        this.purchaseId = purchaseId;
        this.seatTypeId = seatTypeId;
        this.seatId = seatId;
    }

    // Getters y Setters
    @JsonProperty("passengerId")
    public Integer getPassengerId() { return passengerId; }
    public void setPassengerId(Integer passengerId) { this.passengerId = passengerId; }

    @JsonProperty("dni")
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    @JsonProperty("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("age")
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @JsonProperty("country")
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @JsonProperty("boardingPassId")
    public Integer getBoardingPassId() { return boardingPassId; }
    public void setBoardingPassId(Integer boardingPassId) { this.boardingPassId = boardingPassId; }

    @JsonProperty("purchaseId")
    public Integer getPurchaseId() { return purchaseId; }
    public void setPurchaseId(Integer purchaseId) { this.purchaseId = purchaseId; }

    @JsonProperty("seatTypeId")
    public Integer getSeatTypeId() { return seatTypeId; }
    public void setSeatTypeId(Integer seatTypeId) { this.seatTypeId = seatTypeId; }

    @JsonProperty("seatId")
    public Integer getSeatId() { return seatId; }
    public void setSeatId(Integer seatId) { this.seatId = seatId; }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", boardingPassId=" + boardingPassId +
                ", purchaseId=" + purchaseId +
                ", seatTypeId=" + seatTypeId +
                ", seatId=" + seatId +
                '}';
    }
}
