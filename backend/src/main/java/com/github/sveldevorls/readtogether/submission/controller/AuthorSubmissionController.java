package com.github.sveldevorls.readtogether.submission.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponse;
import com.github.sveldevorls.readtogether.security.JwtUserPrincipal;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.service.AuthorSubmissionService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api/submissions/authors", produces = "application/json")
public class AuthorSubmissionController {

    private final AuthorSubmissionService authorSubmissionService;

    public AuthorSubmissionController(AuthorSubmissionService authorSubmissionService) {
        this.authorSubmissionService = authorSubmissionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getAuthorSubmissionDetails(@PathVariable int id) {
        AuthorSubmissionResponse result = authorSubmissionService.getSubmissionById(id);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, result),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessResponse> createNewAuthorSubmission(
            @Valid @RequestBody NewAuthorSubmissionRequest request,
            @AuthenticationPrincipal JwtUserPrincipal principal) {

        int submitterId = principal.getId();
        int createdId = authorSubmissionService.createNewAuthorSubmission(submitterId, request);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.CREATED, Map.of("id", createdId)),
                HttpStatus.CREATED);
    }
}
