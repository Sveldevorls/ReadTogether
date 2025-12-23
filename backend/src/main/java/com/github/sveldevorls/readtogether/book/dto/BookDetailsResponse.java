package com.github.sveldevorls.readtogether.book.dto;

import java.util.List;

import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

public record BookDetailsResponse(

    // Book
    BookResponse book,

    // Link to authors
    List<AuthorSummary> authors,

    // Link to genres
    List<GenreSummary> genres
) {}
