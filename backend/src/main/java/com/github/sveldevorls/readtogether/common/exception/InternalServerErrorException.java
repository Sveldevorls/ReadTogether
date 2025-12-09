package com.github.sveldevorls.readtogether.common.exception;

public class InternalServerErrorException extends RuntimeException{

    public String getErrorMessage() {
        return "Internal server error";
    }
}
