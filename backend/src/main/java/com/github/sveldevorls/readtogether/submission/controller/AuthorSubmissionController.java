package com.github.sveldevorls.readtogether.submission.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;
import com.github.sveldevorls.readtogether.security.JwtUserPrincipal;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionDTO;
import com.github.sveldevorls.readtogether.submission.service.AuthorSubmissionService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api/submissions/authors", produces = "application/json")
public class AuthorSubmissionController {

    private final AuthorSubmissionService submissionService;

    public AuthorSubmissionController(AuthorSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessResponseDTO> createNewAuthorSubmission(
            @Valid @RequestBody AuthorSubmissionDTO dto,
            @AuthenticationPrincipal JwtUserPrincipal principal) {

        int submitterId = principal.getId();
        int createdId = submissionService.createNewAuthorSubmission(submitterId, dto);

        return new ResponseEntity<>(
                new SuccessResponseDTO(HttpStatus.CREATED, Map.of("id", createdId)),
                HttpStatus.CREATED);
    }
}
