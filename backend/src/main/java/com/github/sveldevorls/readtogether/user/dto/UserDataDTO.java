package com.github.sveldevorls.readtogether.user.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.user.entity.User;

// Basic user data, different from UserProfile where other fields for books and authors will exist
public record UserDataDTO(
    String username,
    String displayName,
    String avatarUrl,
    String bio,
    Instant createdAt,
    String userRole
) { 

    public static UserDataDTO fromEntity(User user) {
        return new UserDataDTO(
            user.getUsername(), 
            user.getDisplayName(), 
            user.getAvatarUrl(), 
            user.getBio(), 
            user.getCreatedAt(), 
            user.getUserRole().name()
        );
    }
}
