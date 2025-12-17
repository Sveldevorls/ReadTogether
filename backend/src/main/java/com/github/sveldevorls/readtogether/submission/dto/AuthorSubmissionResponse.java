package com.github.sveldevorls.readtogether.submission.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.submission.entity.ReviewStatus;

public record AuthorSubmissionResponse(

    // Meta
    int id,
    Instant createdAt,
    Instant updatedAt,
    
    // Submission data
    Integer previousSubmissionId,
    int authorId,
    String submitterUsername,
    String submitterComment,
    String reviewerUsername,
    String reviewerComment,
    Instant reviewedAt,
    ReviewStatus reviewStatus,

    // Author data
    AuthorData authorData
) {}
