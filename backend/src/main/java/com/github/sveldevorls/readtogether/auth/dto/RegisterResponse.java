package com.github.sveldevorls.readtogether.auth.dto;

import com.github.sveldevorls.readtogether.user.dto.UserDataResponse;

public record RegisterResponse(
    String token,
    UserDataResponse user
) {}
