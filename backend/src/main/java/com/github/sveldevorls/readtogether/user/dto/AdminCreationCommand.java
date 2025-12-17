package com.github.sveldevorls.readtogether.user.dto;

public record AdminCreationCommand(
    String username,
    String email,
    String password
) {}
