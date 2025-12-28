package com.github.sveldevorls.readtogether.home;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.book.service.BookService;
import com.github.sveldevorls.readtogether.review.dto.FeaturedReviewResponse;
import com.github.sveldevorls.readtogether.review.service.ReviewService;

@Service
public class HomeService {

    private final ReviewService reviewService;

    private final BookService bookService;

    public HomeService(ReviewService reviewService , BookService bookService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
    }
    
    public HomepageResponse getHomepageData() {
        List<FeaturedReviewResponse> featuredReviews = reviewService.getFeaturedReviews();
        List<BookSummary> weeklyPopularBooks = bookService.getWeeklyPopularBooks();
        List<BookSummary> latestBooks = bookService.getLatestBooks();
        return new HomepageResponse(
                featuredReviews,
                weeklyPopularBooks,
                latestBooks
        );
    }
}
