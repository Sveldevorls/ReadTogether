package com.github.sveldevorls.readtogether.author.dao;

import com.github.sveldevorls.readtogether.author.entity.Author;

public interface AuthorDao {

    // C
    // Return: id of the created author
    int createAuthor(Author author); 
}
