package com.github.sveldevorls.readtogether.user.dto;

import com.github.sveldevorls.readtogether.user.entity.User;

public record UserProfileDTO(
    String username,
    String displayName,
    String avatarUrl,
    String bio,
    String createdAt,
    String userRole
) { 

    public static UserProfileDTO fromEntity(User user) {
        return new UserProfileDTO(
            user.username(), 
            user.displayName(), 
            user.avatarUrl(), 
            user.bio(), 
            user.createdAt(), 
            user.userRole().name()
        );
    }
}
