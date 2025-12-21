package com.github.sveldevorls.readtogether.book.dto;

import java.time.Instant;

import com.github.sveldevorls.readtogether.book.entity.BookData;

public record BookResponse(

    int id,
    String slug,
    Instant createdAt,
    Instant updatedAt,
    BookData bookData
) {}
