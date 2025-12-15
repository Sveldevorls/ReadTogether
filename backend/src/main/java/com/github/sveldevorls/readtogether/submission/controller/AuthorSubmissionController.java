package com.github.sveldevorls.readtogether.submission.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;
import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionDTO;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api/submissions/authors", produces = "application/json")
public class AuthorSubmissionController {

    // Todo: expand
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessResponseDTO> createNewAuthorSubmission(
            @Valid @RequestBody NewAuthorSubmissionDTO dto) {
        System.out.println(dto);
        return null;
    }
}
