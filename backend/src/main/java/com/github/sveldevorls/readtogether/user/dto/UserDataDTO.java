package com.github.sveldevorls.readtogether.user.dto;

import com.github.sveldevorls.readtogether.user.entity.User;

// Basic user data, different from UserProfile where other fields for books and authors will exist
public record UserDataDTO(
    String username,
    String displayName,
    String avatarUrl,
    String bio,
    String createdAt,
    String userRole
) { 

    public static UserDataDTO fromEntity(User user) {
        return new UserDataDTO(
            user.username(), 
            user.displayName(), 
            user.avatarUrl(), 
            user.bio(), 
            user.createdAt(), 
            user.userRole().name()
        );
    }
}
