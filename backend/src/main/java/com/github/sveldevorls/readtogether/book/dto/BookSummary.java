package com.github.sveldevorls.readtogether.book.dto;

import java.util.List;

import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

public record BookSummary(

        List<AuthorSummary> authors,
        int id,
        String slug,
        String title,
        String coverUrl
) {}
