package com.github.sveldevorls.readtogether.author.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.author.dao.AuthorDAO;
import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.author.entity.AuthorMapper;

@Service
public class AuthorService {

    private final AuthorDAO authorDao;

    public AuthorService(AuthorDAO authorDao) {
        this.authorDao = authorDao;
    }

    public int fromData(AuthorData authorData) {
        Author author = AuthorMapper.toEntity(authorData);
        int createdId = authorDao.createAuthor(author);

        return createdId;
    }
}
