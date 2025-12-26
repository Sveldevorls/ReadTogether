package com.github.sveldevorls.readtogether.review.dto;

public record ReviewSubmissionResponse(

        RatingsSummary ratings,
        ReviewResponse userReview
) {}
