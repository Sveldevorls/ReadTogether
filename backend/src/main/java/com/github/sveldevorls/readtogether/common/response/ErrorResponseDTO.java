package com.github.sveldevorls.readtogether.common.response;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(int statusCode, String message, List<Map<String, String>> errors) {

    public ErrorResponseDTO{
        errors = errors != null ? errors : Collections.emptyList();
    }

    // Status only -> {statusCode: xxx, message: statusReasonPhrase, errors: []}
    public ErrorResponseDTO(HttpStatus status) {
        this(status, Collections.emptyList());
    }

    // Status and errors -> {statusCode: xxx, message: statusReasonPhrase, errors: customErrors}
    public ErrorResponseDTO(HttpStatus status, List<Map<String, String>>errors) {
        this(status, status.getReasonPhrase(), errors);
    }

    // All -> {statusCode: xxx, message: customMessage, errors: customErrors}
    public ErrorResponseDTO(HttpStatus status, String customMessage, List<Map<String, String>> errors) {
        this(status.value(), customMessage, errors);
    }
}
