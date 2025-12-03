package com.github.sveldevorls.readtogether.auth.exception;

public class InvalidLoginCredentialsException extends RuntimeException{

    public String getErrorMessage() {
        return "Invalid username/email or password";
    }
}
