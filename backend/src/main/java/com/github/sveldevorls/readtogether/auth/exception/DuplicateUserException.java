package com.github.sveldevorls.readtogether.auth.exception;

import java.util.List;

public class DuplicateUserException extends RuntimeException {

    private List<String> errorFields;

    public DuplicateUserException(List<String> errorFields) {
        this.errorFields = errorFields;
    }
    
    public List<String> getErrorFields() {
        return this.errorFields;
    }

    public String getErrorMessage(String field) {
        return String.format("This %s has already been registered", field);
    }
}
