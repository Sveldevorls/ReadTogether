package com.github.sveldevorls.readtogether.auth.dto;

import com.github.sveldevorls.readtogether.user.dto.UserDataDTO;

public record VerifyResponseDTO(
    UserDataDTO user
) {}
