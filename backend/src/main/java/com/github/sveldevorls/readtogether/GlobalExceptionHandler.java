package com.github.sveldevorls.readtogether;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.sveldevorls.readtogether.responses.ErrorResponse;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> errors.add(Map.of(error.getField(), error.getDefaultMessage())));

        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }
}
