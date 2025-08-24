package com.andes.airlinecheckin.exception;

import com.andes.airlinecheckin.dto.FlightResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FlightResponse handleDataAccessException(DataAccessException e) {
        FlightResponse response = new FlightResponse();
        response.setCode(400);
        response.setErrors("could not connect to db");
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FlightResponse handleRuntimeException(RuntimeException e) {
        FlightResponse response = new FlightResponse();
        response.setCode(400);
        response.setErrors(e.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FlightResponse handleException(Exception e) {
        FlightResponse response = new FlightResponse();
        response.setCode(500);
        response.setErrors("Internal server error");
        return response;
    }
}
