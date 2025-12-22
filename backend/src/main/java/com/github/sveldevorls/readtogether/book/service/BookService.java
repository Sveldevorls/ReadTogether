package com.github.sveldevorls.readtogether.book.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.book.dao.BookDao;
import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.book.mapper.BookMapper;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;

@Service
public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    // C
    public int createFromBookData(BookData bookData) {
        Book book = BookMapper.toEntity(bookData);
        int createdId = bookDao.createBook(book);

        return createdId;
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
