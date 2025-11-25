package com.github.sveldevorls.readtogether.register;

import com.github.sveldevorls.readtogether.responses.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> errors = new HashMap<>();
        String password = request.getPassword();
        String passwordConfirm = request.getPasswordConfirm();
        if (!password.equals(passwordConfirm)) {
            errors.put("passwordConfirm", "Password does not match confirmation");
        }

        if (errors.isEmpty()) {
            System.out.println("Validation success");
            return new ResponseEntity<>("Good", HttpStatus.CREATED);
        } else {
            System.out.println("Validation failed");
            return new ResponseEntity<>(new ErrorResponse(400, errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<ErrorResponse> doGet() {
        return new ResponseEntity<>(
                new ErrorResponse(405, Map.of("general", "Method Not Allowed")),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
}
