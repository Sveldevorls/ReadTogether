package com.github.sveldevorls.readtogether.submission.dto;

import java.time.Instant;
import java.util.List;

import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

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
    List<AuthorLink> authors,

    // List of genres
    List<GenreLink> genres
) {}
