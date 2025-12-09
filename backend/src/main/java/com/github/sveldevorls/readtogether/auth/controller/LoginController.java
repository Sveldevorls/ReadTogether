package com.github.sveldevorls.readtogether.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.auth.dto.LoginRequestDTO;
import com.github.sveldevorls.readtogether.auth.service.AuthService;
import com.github.sveldevorls.readtogether.common.response.ErrorResponseDTO;
import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/login", produces = "application/json")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // placeholder, add jwt later
    @PostMapping
    public ResponseEntity<SuccessResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        authService.login(request);
        return new ResponseEntity<>(
                new SuccessResponseDTO(HttpStatus.OK, "Logged in successfully", null),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ErrorResponseDTO> doGet() {
        return new ResponseEntity<>(
                new ErrorResponseDTO(HttpStatus.METHOD_NOT_ALLOWED),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
}

