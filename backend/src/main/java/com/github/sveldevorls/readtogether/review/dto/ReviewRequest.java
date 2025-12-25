package com.github.sveldevorls.readtogether.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewRequest(

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at least 5")
    int rating,

    String content
) {}
