package com.github.sveldevorls.readtogether.auth.dto;

import com.github.sveldevorls.readtogether.user.dto.UserDataDTO;

public record RegisterResponseDTO(
    String token,
    UserDataDTO user
) {}
