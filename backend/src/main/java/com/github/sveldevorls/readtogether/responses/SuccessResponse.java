package com.github.sveldevorls.readtogether.responses;

import java.util.Collections;

import org.springframework.http.HttpStatus;

public class SuccessResponse {

    private final int statusCode;
    private final String message;
    private final Object data;

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
        this.statusCode = status.value();
        this.message = customMessage;
        this.data = data != null ? data : Collections.emptyList();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
