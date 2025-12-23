package com.github.sveldevorls.readtogether.genres.mapper;

import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.genres.entity.Genre;

public class GenreMapper {

    public static GenreSummary toResponse(Genre genre) {
        return new GenreSummary(
            genre.getId(),
            genre.getSlug(),
            genre.getGenreName()
        );
    }
}
