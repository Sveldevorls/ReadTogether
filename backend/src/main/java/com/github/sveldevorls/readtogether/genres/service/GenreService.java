package com.github.sveldevorls.readtogether.genres.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.genres.dao.GenreDao;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.genres.entity.Genre;
import com.github.sveldevorls.readtogether.genres.mapper.GenreMapper;

@Service
public class GenreService {

    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public List<GenreSummary> getAll() {
        List<Genre> allGenres = genreDao.getAll();
        return allGenres.stream()
                .map((genre) -> GenreMapper.toResponse(genre))
                .toList();
    }
}
