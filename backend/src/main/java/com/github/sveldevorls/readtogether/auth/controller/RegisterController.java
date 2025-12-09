package com.github.sveldevorls.readtogether.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.auth.service.AuthService;
import com.github.sveldevorls.readtogether.common.response.ErrorResponseDTO;
import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/register", produces = "application/json")
public class RegisterController {

    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return new ResponseEntity<>(
                new SuccessResponseDTO(HttpStatus.CREATED, "Registration completed", null),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ErrorResponseDTO> doGet() {
        return new ResponseEntity<>(
                new ErrorResponseDTO(HttpStatus.METHOD_NOT_ALLOWED),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
}
