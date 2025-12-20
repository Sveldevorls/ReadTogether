package com.github.sveldevorls.readtogether.author.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.author.dto.AuthorResponse;
import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.common.response.SuccessResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/api/authors", produces = "application/json")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResponse> getAuthor(@PathVariable int id) {
        AuthorResponse response = authorService.getAuthorById(id);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }

    // /authors?name=
    @GetMapping()
    public ResponseEntity<SuccessResponse> searchByName(@RequestParam String name) {
        List<AuthorResponse> response = authorService.searchApprovedAuthorsByName(name);
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }

}
