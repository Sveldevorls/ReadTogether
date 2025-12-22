package com.github.sveldevorls.readtogether.submission.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.sveldevorls.readtogether.common.response.SuccessResponse;
import com.github.sveldevorls.readtogether.security.JwtUserPrincipal;
import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.NewBookSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.service.BookSubmissionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/api/submissions/books", produces = "application/json")
public class BookSubmissionController {

    private final BookSubmissionService bookSubmissionService;

    public BookSubmissionController(BookSubmissionService bookSubmissionService) {
        this.bookSubmissionService = bookSubmissionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SuccessResponse> createNewBookSubmission(
            @Valid @RequestBody NewBookSubmissionRequest request,
            @AuthenticationPrincipal JwtUserPrincipal principal) {

        int submitterId = principal.getId();
        int createdId = bookSubmissionService.createNewBookSubmission(submitterId, request);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.CREATED, Map.of("id", createdId)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SuccessResponse> getSubmissionDetails(@PathVariable int submissionId) {
        BookSubmissionResponse result = bookSubmissionService.getSubmissionResponseById(submissionId);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, result),
                HttpStatus.OK);
    }
}
