package com.andes.airlinecheckin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightResponse {
    private int code;
    private FlightData data;
    private String errors;

    public FlightResponse() {}

    public FlightResponse(int code, FlightData data) {
        this.code = code;
        this.data = data;
    }

    public FlightResponse(int code, String errors) {
        this.code = code;
        this.errors = errors;
    }

    // Getters and setters
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public FlightData getData() { return data; }
    public void setData(FlightData data) { this.data = data; }

    public String getErrors() { return errors; }
    public void setErrors(String errors) { this.errors = errors; }
}
