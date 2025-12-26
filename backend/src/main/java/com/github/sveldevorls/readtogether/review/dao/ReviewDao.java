package com.github.sveldevorls.readtogether.review.dao;

import java.util.List;
import java.util.Optional;

import com.github.sveldevorls.readtogether.review.dto.RatingsSummary;
import com.github.sveldevorls.readtogether.review.dto.ReviewResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewSummary;
import com.github.sveldevorls.readtogether.review.entity.Review;

public interface ReviewDao {
    
    // C
    int createReview(Review review);

    // R
    Optional<ReviewSummary> getReviewSummaryById(int id);

    Optional<ReviewResponse> getUserBookReview(int userId, int bookId);

    Optional<RatingsSummary> getBookRatingsResponse(int id);

    List<ReviewResponse> getCommunityReviewsByBookId(int bookId, Integer userId);
}
