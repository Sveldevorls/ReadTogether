package com.github.sveldevorls.readtogether.author.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.author.dao.AuthorDao;
import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.author.entity.AuthorMapper;

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
        authorDao.updateIsPendingById(id);
    }

    public void deleteAuthor(int id) {
        authorDao.deleteById(id);
    }
}
