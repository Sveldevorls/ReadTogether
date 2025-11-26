package com.github.sveldevorls.readtogether.responses;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final int statusCode;
    private final String message;
    private final List<Map<String, String>> errors;

    // Status only -> {statusCode: xxx, message: statusReasonPhrase, errors: []}
    public ErrorResponse(HttpStatus status) {
        this(status, Collections.emptyList());
    }

    // Status and errors -> {statusCode: xxx, message: statusReasonPhrase, errors: customErrors}
    public ErrorResponse(HttpStatus status, List<Map<String, String>>errors) {
        this(status, status.getReasonPhrase(), errors);
    }

    // All -> {statusCode: xxx, message: customMessage, errors: customErrors}
    public ErrorResponse(HttpStatus status, String customMessage, List<Map<String, String>> errors) {
        this.statusCode = status.value();
        this.message = customMessage;
        this.errors = errors != null ? errors : Collections.emptyList();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }
}
