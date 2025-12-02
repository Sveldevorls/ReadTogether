package com.github.sveldevorls.readtogether.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.sveldevorls.readtogether.auth.exception.DuplicateUserException;
import com.github.sveldevorls.readtogether.common.response.ErrorResponseDTO;
import com.github.sveldevorls.readtogether.util.ErrorMapper;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> errors.add(ErrorMapper.map(error.getField(), error.getDefaultMessage())));

        return new ResponseEntity<>(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateUserException(DuplicateUserException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getErrorFields()
          .forEach(field -> errors.add(ErrorMapper.map(field, ex.getErrorMessage(field))));

        return new ResponseEntity<>(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, errors), HttpStatus.BAD_REQUEST);
    }
}
