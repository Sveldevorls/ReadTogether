package com.github.sveldevorls.readtogether.author.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.author.dao.AuthorDao;
import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.author.entity.AuthorMapper;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;

@Service
public class AuthorService {

    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public int createFromAuthorData(AuthorData authorData) {
        Author author = AuthorMapper.toEntity(authorData);
        int createdId = authorDao.createAuthor(author);

        return createdId;
    }

    public void approveAuthor(int id) {
        int rows = authorDao.updateReviewStatusById(id, ReviewStatus.approved);

        if (rows == 0) throw new ResourceNotFoundException("Author was not found, try refreshing the page");
        if (rows > 1) throw new DataIntegrityViolationException("Multiple authors were found when only one was expected");
    }

    public void deleteAuthor(int id) {
        int rows = authorDao.updateReviewStatusById(id, ReviewStatus.rejected);

        if (rows == 0) throw new ResourceNotFoundException("Author was not found, try refreshing the page");
        if (rows > 1) throw new DataIntegrityViolationException("Multiple authors were found when only one was expected");
    }
}
