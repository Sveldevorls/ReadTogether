package com.github.sveldevorls.readtogether.auth;

public class DuplicateUserException extends RuntimeException {

    private String field;
    private String errorMessage;

    public DuplicateUserException(String field) {
        this.field = field;
        this.errorMessage = String.format("This %s has already been registered", field);
    }
    
    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
