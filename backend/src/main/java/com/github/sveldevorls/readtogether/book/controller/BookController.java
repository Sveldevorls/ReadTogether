package com.github.sveldevorls.readtogether.book.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.author.dto.AuthorProfileResponse;
import com.github.sveldevorls.readtogether.author.dto.AuthorResponse;
import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.book.dto.BookDetailsResponse;
import com.github.sveldevorls.readtogether.book.dto.BookResponse;
import com.github.sveldevorls.readtogether.book.service.BookService;
import com.github.sveldevorls.readtogether.common.response.SuccessResponse;

@RestController
@RequestMapping(path = "/api/books", produces = "application/json")
public class BookController {

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

    // Todo: expand once books are added
    @GetMapping(path = "/{id}/details")
    public ResponseEntity<SuccessResponse> getBookDetails(@PathVariable int id) {
        BookDetailsResponse response = bookService.getBookDetailsById(id);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }
}
