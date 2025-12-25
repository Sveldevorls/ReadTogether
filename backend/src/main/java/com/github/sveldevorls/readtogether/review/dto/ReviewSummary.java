package com.github.sveldevorls.readtogether.review.dto;

import java.time.Instant;

public record ReviewSummary(

    // Meta
    int id,
    Instant createdAt,
    Instant updatedAt,

    // Review content
    int rating,
    String comment,
    int likeCount,
    boolean isFeatured
) {}
