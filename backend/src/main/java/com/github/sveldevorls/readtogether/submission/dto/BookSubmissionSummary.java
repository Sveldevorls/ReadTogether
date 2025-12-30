package com.github.sveldevorls.readtogether.submission.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public record BookSubmissionSummary(

    UserSummary submitter,
    BookSummary book,
    int id,
    Instant createdAt,
    Instant updatedAt,
    String status,
    String type
) {}
