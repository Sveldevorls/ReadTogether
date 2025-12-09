package com.github.sveldevorls.readtogether.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public String getErrorMessage() {
        return "Resource not found";
    }
}
