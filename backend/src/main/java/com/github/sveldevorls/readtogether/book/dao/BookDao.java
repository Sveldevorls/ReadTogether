package com.github.sveldevorls.readtogether.book.dao;

import com.github.sveldevorls.readtogether.book.entity.Book;

public interface BookDao {

    //C
    // Return: id of the created book
    public int createBook(Book book);
}
