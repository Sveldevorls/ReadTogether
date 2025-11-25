package com.github.sveldevorls.readtogether.responses;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final int statusCode;
    private final String message;
    private final Map<String, String> errors;

    public ErrorResponse(int statusCode, Map<String, String> errors) {
        this.statusCode = statusCode;
        this.message = HttpStatus.valueOf(statusCode).getReasonPhrase();
        this.errors = errors;
    }

    public ErrorResponse(int statusCode, String customMessage, Map<String, String> errors) {
        this.statusCode = statusCode;
        this.message = customMessage;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
