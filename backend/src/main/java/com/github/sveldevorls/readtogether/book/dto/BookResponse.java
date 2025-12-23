package com.github.sveldevorls.readtogether.book.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public record BookResponse(

    int id,
    String slug,
    Instant createdAt,
    Instant updatedAt,
    ReviewStatus reviewStatus,
    
    BookData bookData
) {}
