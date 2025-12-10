package com.github.sveldevorls.readtogether.user.dto;

public record UserPageDTO(
    String username,
    String displayName,
    String avatarUrl,
    String bio,
    String createdAt,
    String userRole
) { }
