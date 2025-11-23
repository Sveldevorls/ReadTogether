package com.github.sveldevorls.readtogether.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserRegisterController {

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/api/user/register")
    public String registerUser(@RequestBody UserRegisterRequest request) {
        return request.getUsername();
    }

    @GetMapping("/api/user/register")
    public String getRegisterInfo() {
        return "User registration endpoint.";
    }
}
