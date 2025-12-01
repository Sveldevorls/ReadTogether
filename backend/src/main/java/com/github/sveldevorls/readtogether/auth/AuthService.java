package com.github.sveldevorls.readtogether.auth;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.register.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.user.service.UserService;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(RegisterRequestDTO dto) {
        userService.createUser(dto);
    }
}
