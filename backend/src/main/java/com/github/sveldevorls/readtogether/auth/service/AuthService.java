package com.github.sveldevorls.readtogether.auth.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
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
