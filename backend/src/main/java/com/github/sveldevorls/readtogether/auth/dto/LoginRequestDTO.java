package com.github.sveldevorls.readtogether.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
    
    @NotBlank(message = "Username or email is required")
    String identifier,
    
    @NotBlank(message = "Password is required")
    String password
) {}
