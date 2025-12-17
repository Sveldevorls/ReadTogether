package com.github.sveldevorls.readtogether.common.response;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int statusCode, String message, List<Map<String, String>> errors) {

    public ErrorResponse{
        errors = errors != null ? errors : Collections.emptyList();
    }

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
        this(status.value(), customMessage, errors);
    }
}
