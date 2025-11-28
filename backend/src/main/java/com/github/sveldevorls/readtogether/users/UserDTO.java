package com.github.sveldevorls.readtogether.users;

public record UserDTO(
    
    String username,
    String displayName,
    String pfpURL,
    String aboutMe,
    String createdAt,
    String role
) { }
