package com.github.sveldevorls.readtogether.user;

public record UserEntity(
    int id,
    String username,
    String email,
    String displayName,
    String passwordHash,
    String avatarUrl,
    String bio,
    String createdAt,
    String updatedAt,
    String userRole
) {}