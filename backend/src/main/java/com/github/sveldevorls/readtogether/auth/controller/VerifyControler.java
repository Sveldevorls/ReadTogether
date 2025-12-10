package com.github.sveldevorls.readtogether.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.auth.dto.VerifyResponseDTO;
import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(path = "/api/verify", produces = "application/json")
public class VerifyControler {

    @PostMapping
    public ResponseEntity<SuccessResponseDTO> verify(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        return new ResponseEntity<>(
                new SuccessResponseDTO(
                    HttpStatus.OK,
                    new VerifyResponseDTO(username, role)),
                HttpStatus.OK);
    }

}
