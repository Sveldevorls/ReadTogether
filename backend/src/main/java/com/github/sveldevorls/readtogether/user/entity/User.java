package com.github.sveldevorls.readtogether.user.entity;

public record User(
    Integer id,
    String searchKey,
    String createdAt,
    String updatedAt,
    String username,
    String email,
    String displayName,
    String passwordHash,
    String avatarUrl,
    String bio,
    String userRole
) {
    public static User createUser(String username, String email, String hashedPassword) {
        return new User(
            null,
            username,
            null,
            null,
            username,
            email,
            null,
            hashedPassword,
            null,
            null,
            "user"
        );
    }

    public static User createAdmin(String username, String email, String hashedPassword) {
        return new User(
            null,
            username,
            null,
            null,
            username,
            email,
            null,
            hashedPassword,
            null,
            null,
            "admin"
        );
    }
}