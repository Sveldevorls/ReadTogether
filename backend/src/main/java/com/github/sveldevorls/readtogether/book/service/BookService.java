package com.github.sveldevorls.readtogether.book.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.book.dao.BookDao;
import com.github.sveldevorls.readtogether.book.dto.BookDetailsResponse;
import com.github.sveldevorls.readtogether.book.dto.BookResponse;
import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.book.mapper.BookMapper;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

@Service
public class BookService {

    private final BookDao bookDao;

    public BookService(AuthorService authorService, BookDao bookDao) {
        this.bookDao = bookDao;
    }

    // C
    public int createFromBookData(BookData bookData) {
        Book book = BookMapper.toEntity(bookData);
        int createdId = bookDao.createBook(book);

        return createdId;
    }

    // R
    public List<AuthorSummary> getAuthorSummariesById(int id) {
        List<AuthorSummary> result = bookDao.getAuthorSummariesById(id);
        return result;
    }

    public List<GenreSummary> getGenreSummariesById(int id) {
        List<GenreSummary> result = bookDao.getGenreSummariesById(id);
        return result;
    }

    public BookResponse getBookById(int id) {
        Book book = bookDao.getBookById(id)
                            .orElseThrow(() -> new ResourceNotFoundException());
        BookResponse response = BookMapper.toResponse(book);
        
        return response;
    }

    public BookDetailsResponse getBookDetailsById(int id) {
        // Book response
        Book book = bookDao.getBookById(id)
                           .orElseThrow(() -> new ResourceNotFoundException());
        BookResponse bookResponse = BookMapper.toResponse(book);
        
        // Author summaries
        List<AuthorSummary> authors = getAuthorSummariesById(id);

        // Genre summaries
        List<GenreSummary> genres = getGenreSummariesById(id);

        return new BookDetailsResponse(
            bookResponse,
            authors,
            genres
        );
    }

    // U
    public void approveBook(int id) {
        int rows = bookDao.updateReviewStatusById(id, ReviewStatus.approved);

        if (rows == 0)
            throw new ResourceNotFoundException("Book was not found, try refreshing the page");
        if (rows > 1)
            throw new DataIntegrityViolationException("Multiple books were found when only one was expected");
    }

    public void mapBookAuthor(int bookId, int authorId) {
        bookDao.mapBookAuthor(bookId, authorId);
    }

    public void mapBookGenre(int bookId, int genreId) {
        bookDao.mapBookGenre(bookId, genreId);
    }

    public void rejectBook(int id) {
        int rows = bookDao.updateReviewStatusById(id, ReviewStatus.rejected);

        if (rows == 0)
            throw new ResourceNotFoundException("Book was not found, try refreshing the page");
        if (rows > 1)
            throw new DataIntegrityViolationException("Multiple books were found when only one was expected");
    }

}
