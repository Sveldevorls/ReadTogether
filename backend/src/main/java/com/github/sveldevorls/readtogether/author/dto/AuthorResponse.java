package com.github.sveldevorls.readtogether.author.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;

public record AuthorResponse(

    int id,
    String slug,
    Instant createdAt,
    Instant updatedAt,
    AuthorData authorData
) {}
