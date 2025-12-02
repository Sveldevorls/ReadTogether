package com.github.sveldevorls.readtogether.auth.dto;

import com.github.sveldevorls.readtogether.auth.validation.PasswordsMatch;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordsMatch
public record RegisterRequestDTO(

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    @Size(max = 20, message = "Username must be at most 20 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is not valid")
    String email,
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 64, message = "Password must be at most 64 characters long")
    String password,

    @NotBlank(message = "Password confirmation is required")
    String passwordConfirm
){}
