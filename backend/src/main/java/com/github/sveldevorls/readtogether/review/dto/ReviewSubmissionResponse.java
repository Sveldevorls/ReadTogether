package com.github.sveldevorls.readtogether.review.dto;

public record ReviewSubmissionResponse(

        RatingsSummary ratings,
        ReviewSummary userReview
) {}
