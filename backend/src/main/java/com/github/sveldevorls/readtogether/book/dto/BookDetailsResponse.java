package com.github.sveldevorls.readtogether.book.dto;

import java.util.List;

import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.review.dto.RatingsSummary;
import com.github.sveldevorls.readtogether.review.dto.ReviewSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

public record BookDetailsResponse(

    // Book
    BookResponse book,

    // Link to authors
    List<AuthorSummary> authors,

    // Link to genres
    List<GenreSummary> genres,

    // Ratings summary
    RatingsSummary ratings,

    // User review
    ReviewSummary userReview

    /* // Community reviews
    List<ReviewResponse> communityReviews */
) {}
