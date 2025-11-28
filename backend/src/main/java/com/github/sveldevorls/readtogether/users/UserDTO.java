package com.github.sveldevorls.readtogether.users;

public record UserDTO(
    
    String username,
    String displayName,
    String avatarUrl,
    String bio,
    String createdAt,
    String role
) { }
