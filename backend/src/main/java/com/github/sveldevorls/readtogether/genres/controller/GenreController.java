package com.github.sveldevorls.readtogether.genres.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponse;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.genres.service.GenreService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/api/genres", produces = "application/json")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping()
    public ResponseEntity<SuccessResponse> getAllGenres() {
        List<GenreSummary> response = genreService.getAll();
        return new ResponseEntity<>(
                new SuccessResponse(HttpStatus.OK, response),
                HttpStatus.OK);
    }

}
