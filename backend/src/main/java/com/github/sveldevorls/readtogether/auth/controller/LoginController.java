package com.github.sveldevorls.readtogether.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.auth.dto.LoginRequest;
import com.github.sveldevorls.readtogether.auth.dto.LoginResponse;
import com.github.sveldevorls.readtogether.auth.service.AuthService;
import com.github.sveldevorls.readtogether.common.response.ErrorResponse;
import com.github.sveldevorls.readtogether.common.response.SuccessResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/login", produces = "application/json")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return new ResponseEntity<>(
                new SuccessResponse(
                    HttpStatus.OK, 
                    "Logged in successfully", 
                    response),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ErrorResponse> doGet() {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
}

