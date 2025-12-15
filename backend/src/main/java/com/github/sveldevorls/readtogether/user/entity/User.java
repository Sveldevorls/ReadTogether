package com.github.sveldevorls.readtogether.user.entity;

public record User(
    Integer id,
    String createdAt,
    String updatedAt,
    String username,
    String email,
    String displayName,
    String passwordHash,
    String avatarUrl,
    String bio,
    Role userRole
) {
    public static User createUser(String username, String email, String hashedPassword) {
        return new User(
            null,
            null,
            null,
            username,
            email,
            null,
            hashedPassword,
            null,
            null,
            Role.ROLE_USER
        );
    }

    public static User createAdmin(String username, String email, String hashedPassword) {
        return new User(
            null,
            null,
            null,
            username,
            email,
            null,
            hashedPassword,
            null,
            null,
            Role.ROLE_ADMIN
        );
    }
}