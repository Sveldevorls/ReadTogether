package com.github.sveldevorls.readtogether.home;

import java.util.List;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.review.dto.FeaturedReviewResponse;

public record HomepageResponse(

    List<FeaturedReviewResponse> featuredReviews,
    List<BookSummary> weeklyPopularBooks,
    List<BookSummary> latestBooks
) {}
