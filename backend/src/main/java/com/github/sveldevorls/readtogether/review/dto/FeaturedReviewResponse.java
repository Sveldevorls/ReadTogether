package com.github.sveldevorls.readtogether.review.dto;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public record FeaturedReviewResponse(

        BookSummary book,
        UserSummary reviewer,
        ReviewSummary content
) {}
