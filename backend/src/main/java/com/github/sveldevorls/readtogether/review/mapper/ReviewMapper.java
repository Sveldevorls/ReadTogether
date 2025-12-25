package com.github.sveldevorls.readtogether.review.mapper;

import com.github.sveldevorls.readtogether.review.entity.Review;

public class ReviewMapper {
    
    public static Review toEntity(int userId, int bookId, int rating, String comment) {
        Review review = new Review();

        review.setUserId(userId);
        review.setBookId(bookId);
        review.setRating(rating);
        review.setComment(comment);

        return review;
    }
}
