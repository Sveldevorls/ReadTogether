package com.github.sveldevorls.readtogether.register;

import com.github.sveldevorls.readtogether.auth.AuthService;
import com.github.sveldevorls.readtogether.responses.ErrorResponseDTO;
import com.github.sveldevorls.readtogether.responses.SuccessResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/register", produces = "application/json")
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {

    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
        authService.registerUser(request);
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
