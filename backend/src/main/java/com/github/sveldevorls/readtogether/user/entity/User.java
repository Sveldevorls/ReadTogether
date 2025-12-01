package com.github.sveldevorls.readtogether.user.entity;

public record User(
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
) {
    public static User createUser(String username, String email, String hashedPassword) {
        return new User(
            0,
            username,
            email,
            null,
            hashedPassword,
            null,
            null,
            null,
            null,
            "user"
        );
    }
}