package com.github.sveldevorls.readtogether.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.review.dao.ReviewDao;
import com.github.sveldevorls.readtogether.review.dto.RatingsSummary;
import com.github.sveldevorls.readtogether.review.dto.ReviewResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewSummary;
import com.github.sveldevorls.readtogether.review.entity.Review;
import com.github.sveldevorls.readtogether.review.mapper.ReviewMapper;

@Service
public class ReviewService {

    private final ReviewDao reviewDao;

    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    // C
    public int createReview(int userId, int bookId, int rating, String comment) {
        Review review = ReviewMapper.toEntity(userId, bookId, rating, comment);
        int id = reviewDao.createReview(review);

        return id;
    }

    // R
    public ReviewSummary getReviewSummaryById(int id) {
        ReviewSummary response = reviewDao
                .getReviewSummaryById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return response;
    }

    public ReviewSummary getUserBookReview(int userId, int bookId) {
        ReviewSummary response = reviewDao
                .getUserBookReview(userId, bookId)
                .orElse(null);

        return response;
    }

    public RatingsSummary getBookRatingsSummary(int bookId) {
        RatingsSummary response = reviewDao
                .getBookRatingsResponse(bookId)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }

    public List<ReviewResponse> getCommunityReviewsByBookId(int bookId, Integer userId) {
        List<ReviewResponse> response = reviewDao.getCommunityReviewsByBookId(bookId, userId);
        return response;
    }

}
