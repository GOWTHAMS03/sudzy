package com.sudzey.sudzey.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final String details;

    public ErrorResponse(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

}