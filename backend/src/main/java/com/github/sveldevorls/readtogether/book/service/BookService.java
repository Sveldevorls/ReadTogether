package com.github.sveldevorls.readtogether.book.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.book.dao.BookDao;
import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.book.mapper.BookMapper;

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

}
