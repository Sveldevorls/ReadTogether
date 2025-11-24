package com.github.sveldevorls.readtogether.register;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class RegisterController {

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        String password = request.getPassword();
        String passwordConfirm = request.getPasswordConfirm();
        if (!password.equals(passwordConfirm)) {
            return new ResponseEntity<>("Password does not match confirmation", HttpStatus.BAD_REQUEST);
        }
        System.out.println("Validation success");
        return new ResponseEntity<>("Goodx", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(path = "/api/register", produces = "application/json")
    public ResponseEntity<String> doGet() {
        return new ResponseEntity<>("Incorrect format.", HttpStatus.BAD_REQUEST);
    }
}
