package com.github.sveldevorls.readtogether.common.response;

import java.util.Collections;

import org.springframework.http.HttpStatus;

public record SuccessResponse(int statusCode, String message, Object data) {

    public SuccessResponse {
        data = data != null ? data : Collections.emptyList();
    }

    // Status only -> {statusCode: xxx, message: statusReasonPhrase, data: []}
    public SuccessResponse(HttpStatus status) {
        this(status, Collections.emptyList());
    }

    // Status and data -> {statusCode: xxx, message: statusReasonPhrase, data: data}
    public SuccessResponse(HttpStatus status, Object data) {
        this(status, status.getReasonPhrase(), data);
    }

    // All -> {statusCode: xxx, message: customMessage, data: data}
    public SuccessResponse(HttpStatus status, String customMessage, Object data) {
        this(status.value(), customMessage, data);
    }
}
