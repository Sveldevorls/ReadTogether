package com.github.sveldevorls.readtogether.author.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.author.dao.AuthorDAO;

@Service
public class AuthorService {

    private final AuthorDAO authorDao;

    public AuthorService(AuthorDAO authorDao) {
        this.authorDao = authorDao;
    }

    // Todo: implement method
    public int createAuthor() {

        return 1;
    }
}
