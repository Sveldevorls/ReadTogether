package com.github.sveldevorls.readtogether.responses;

import java.util.Collections;

import org.springframework.http.HttpStatus;

public record SuccessResponseDTO(int statusCode, String message, Object data) {

    public SuccessResponseDTO {
        data = data != null ? data : Collections.emptyList();
    }

    // Status only -> {statusCode: xxx, message: statusReasonPhrase, data: []}
    public SuccessResponseDTO(HttpStatus status) {
        this(status, Collections.emptyList());
    }

    // Status and data -> {statusCode: xxx, message: statusReasonPhrase, data: data}
    public SuccessResponseDTO(HttpStatus status, Object data) {
        this(status, status.getReasonPhrase(), data);
    }

    // All -> {statusCode: xxx, message: customMessage, data: data}
    public SuccessResponseDTO(HttpStatus status, String customMessage, Object data) {
        this(status.value(), customMessage, data);
    }
}
