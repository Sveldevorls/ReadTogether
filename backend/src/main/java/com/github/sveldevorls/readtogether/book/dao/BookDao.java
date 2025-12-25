package com.github.sveldevorls.readtogether.book.dao;

import java.util.List;
import java.util.Optional;

import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

public interface BookDao {

    // C
    // Return: id of the created book
    int createBook(Book book);

    void mapBookAuthor(int bookId, int authorId);

    void mapBookGenre(int bookId, int genreId);

    // R
    Optional<Book> getBookById(int id);

    List<AuthorSummary> getAuthorSummariesById(int id);

    List<GenreSummary> getGenreSummariesById(int id);

    // U
    int updateReviewStatusById(int id, ReviewStatus status);
}
