package com.github.sveldevorls.readtogether.auth.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.sveldevorls.readtogether.common.response.ErrorResponse;
import com.github.sveldevorls.readtogether.util.ErrorMapper;

@ControllerAdvice
public class AuthExceptionHandler {
    
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getErrorFields()
          .forEach(field -> errors.add(ErrorMapper.map(field, ex.getErrorMessage(field))));

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginCredentialsException(InvalidLoginCredentialsException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        errors.add(ErrorMapper.map("general", ex.getErrorMessage()));
    
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }
}
