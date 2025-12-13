package com.github.sveldevorls.readtogether.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfileUpdateDTO(
    @Size(max = 30, message = "Display name must be at most 30 characters long", groups = DisplayNameUpdate.class)
    @Pattern(regexp = "^[a-zA-Z0-9_ ]*$", message = "Display name can only contain letters, numbers, spaces, and underscores" , groups = DisplayNameUpdate.class)
    String displayName,

    @Size(max = 500, message = "About me must be at most 500 characters long", groups = BioUpdate.class)
    String bio
) {
    public interface DisplayNameUpdate{}
    public interface BioUpdate{}
}

