package com.github.sveldevorls.readtogether.auth.dto;

import com.github.sveldevorls.readtogether.user.dto.UserProfileDTO;

public record RegisterResponseDTO(
    String token,
    UserProfileDTO user
) {}
