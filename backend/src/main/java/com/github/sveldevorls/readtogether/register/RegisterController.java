package com.github.sveldevorls.readtogether.register;

import com.github.sveldevorls.readtogether.responses.ErrorResponse;

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

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>("Good", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ErrorResponse> doGet() {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
