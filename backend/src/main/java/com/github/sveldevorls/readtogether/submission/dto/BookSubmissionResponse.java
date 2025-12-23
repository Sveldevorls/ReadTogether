package com.github.sveldevorls.readtogether.submission.dto;

import java.time.Instant;
import java.util.List;

import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;

public record BookSubmissionResponse(

    // Meta
    int id,
    Instant createdAt,
    Instant updatedAt,
    
    // Submission data
    Integer previousSubmissionId,
    int bookId,
    String submitterUsername,
    String submitterComment,
    String reviewerUsername,
    String reviewerComment,
    Instant reviewedAt,
    ReviewStatus reviewStatus,

    // Book data
    BookData bookData,

    // List of authors
    List<AuthorSummary> authors,

    // List of genres
    List<GenreSummary> genres
) {}
