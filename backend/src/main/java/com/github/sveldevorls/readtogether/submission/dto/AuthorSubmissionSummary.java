package com.github.sveldevorls.readtogether.submission.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public record AuthorSubmissionSummary(

    UserSummary submitter,
    AuthorSummary author,
    int id,
    Instant createdAt,
    Instant updatedAt,
    String status,
    String type
) {}
