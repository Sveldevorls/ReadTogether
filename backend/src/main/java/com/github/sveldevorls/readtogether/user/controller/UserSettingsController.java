package com.github.sveldevorls.readtogether.user.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;
import com.github.sveldevorls.readtogether.security.JwtUserPrincipal;
import com.github.sveldevorls.readtogether.user.dto.ProfileUpdateDTO;
import com.github.sveldevorls.readtogether.user.dto.ProfileUpdateDTO.BioUpdate;
import com.github.sveldevorls.readtogether.user.dto.ProfileUpdateDTO.DisplayNameUpdate;
import com.github.sveldevorls.readtogether.user.service.UserService;

@RestController
@RequestMapping(path = "/api/me/", produces = "application/json")
public class UserSettingsController {
    
    private final UserService userService;

    public UserSettingsController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/displayName")
    public ResponseEntity<SuccessResponseDTO> patchDisplayName(
            @Validated(DisplayNameUpdate.class) @RequestBody ProfileUpdateDTO dto,
            @AuthenticationPrincipal JwtUserPrincipal principal) {
        String username = principal.getUsername();
        String updatedDisplayName = userService.updateDisplayName(username, dto.displayName());
        return new ResponseEntity<>(
                new SuccessResponseDTO(HttpStatus.OK, Collections.singletonMap("displayName", updatedDisplayName)),
                HttpStatus.OK);
    }

    @PatchMapping("/bio")
    public ResponseEntity<SuccessResponseDTO> patchBio(
            @Validated(BioUpdate.class) @RequestBody ProfileUpdateDTO dto,
            @AuthenticationPrincipal JwtUserPrincipal principal) {
        String username = principal.getUsername();
        String updatedBio = userService.updateBio(username, dto.bio());
        return new ResponseEntity<>(
                new SuccessResponseDTO(HttpStatus.OK, Collections.singletonMap("bio", updatedBio)),
                HttpStatus.OK);
    }
}
