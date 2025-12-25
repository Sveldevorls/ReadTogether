package com.github.sveldevorls.readtogether.review.dto;

import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public record ReviewResponse(

    // Summary of the reviewer
    UserSummary reviewer,

    // Actual review content
    ReviewSummary content
) {}
