package com.sage.epicstudent.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private String message;
    private HttpStatus statusCode;

    public ApiException(String message, HttpStatus statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

}
