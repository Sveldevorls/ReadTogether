package com.github.sveldevorls.readtogether.author.dao;

import java.util.List;
import java.util.Optional;

import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public interface AuthorDao {

    // C
    // Return: id of the created author
    int createAuthor(Author author);

    // R
    Optional<Author> getAuthorById(int id);

    List<Author> searchApprovedAuthorsByName(String name);

    // U 
    int updateReviewStatusById(int id, ReviewStatus status);
    
    /* // D
    int deleteById(int id); */
}
