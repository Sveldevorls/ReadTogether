package com.github.sveldevorls.readtogether.user;

public record UserDTO(
    String username,
    String displayName,
    String avatarUrl,
    String bio,
    String createdAt,
    String userRole
) { }
