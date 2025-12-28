package com.github.sveldevorls.readtogether.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponse;
import com.github.sveldevorls.readtogether.review.service.ReviewService;

@RestController
@RequestMapping(path = "/api/reviews", produces = "application/json")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping(path = "/{id}/featured")
    public ResponseEntity<SuccessResponse> login(@PathVariable int id) {

        reviewService.setReviewAsFeatured(id);
        return new ResponseEntity<>(
                new SuccessResponse(
                        HttpStatus.OK,
                        "Logged in successfully"),
                HttpStatus.OK);
    }

}
