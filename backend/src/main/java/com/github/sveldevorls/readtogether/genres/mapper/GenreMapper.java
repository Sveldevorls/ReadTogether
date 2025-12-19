package com.github.sveldevorls.readtogether.genres.mapper;

import com.github.sveldevorls.readtogether.genres.dto.GenreResponse;
import com.github.sveldevorls.readtogether.genres.entity.Genre;

public class GenreMapper {

    public static GenreResponse toResponse(Genre genre) {
        return new GenreResponse(
            genre.getId(),
            genre.getSlug(),
            genre.getGenreName()
        );
    }
}
