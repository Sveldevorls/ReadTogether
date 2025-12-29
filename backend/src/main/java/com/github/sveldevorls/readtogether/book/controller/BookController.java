package com.github.sveldevorls.readtogether.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.author.dto.AuthorResponse;
import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.book.dto.BookDetailsResponse;
import com.github.sveldevorls.readtogether.book.dto.BookResponse;
import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.book.service.BookService;
import com.github.sveldevorls.readtogether.common.response.SuccessResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewSubmissionResponse;
import com.github.sveldevorls.readtogether.security.JwtUserPrincipal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/api/books", produces = "application/json")
public class BookController {

    private record ReviewRequest(int rating, String comment) {
    }

    private final BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResponse> getBook(@PathVariable int id) {
        BookResponse response = bookService.getBookById(id);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }

    // Todo: expand once reviews are added
    @GetMapping(path = "/{bookId}/details")
    public ResponseEntity<SuccessResponse> getBookDetails(
            @PathVariable int bookId,
            @AuthenticationPrincipal JwtUserPrincipal principal) {

        Integer userId = principal != null ? principal.getId() : null;
        BookDetailsResponse response = bookService.getBookDetailsById(bookId, userId);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }

    /* @GetMapping("/{bookId}/reviews")
    public ResponseEntity<SuccessResponse> getCommunityReviews(
            @PathVariable int bookId,
            @AuthenticationPrincipal JwtUserPrincipal principal) {

        Integer userId = principal != null ? principal.getId() : null;
        List<ReviewResponse> response = bookService.getCommunityReviews(bookId, userId);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    } */

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/{id}/reviews")
    public ResponseEntity<SuccessResponse> rateBook(
            @AuthenticationPrincipal JwtUserPrincipal principal,
            @PathVariable int id,
            @RequestBody ReviewRequest request) {

        ReviewSubmissionResponse response = bookService.reviewBook(
                principal.getId(),
                id,
                request.rating(),
                request.comment());

        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }

    // /books?title=
    @GetMapping()
    public ResponseEntity<SuccessResponse> searchByName(@RequestParam String title) {
        List<BookSummary> response = bookService.searchApprovedBooksByTitle(title);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }
}
