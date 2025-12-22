package com.github.sveldevorls.readtogether.author.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public record AuthorResponse(

    int id,
    String slug,
    Instant createdAt,
    Instant updatedAt,
    ReviewStatus reviewStatus,

    AuthorData authorData
) {}
