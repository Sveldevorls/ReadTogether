package com.github.sveldevorls.readtogether.book.dao;

import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public interface BookDao {

    // C
    // Return: id of the created book
    public int createBook(Book book);

    public void mapBookAuthor(int bookId, int authorId);

    public void mapBookGenre(int bookId, int genreId);

    // U
    int updateReviewStatusById(int id, ReviewStatus status);
}
