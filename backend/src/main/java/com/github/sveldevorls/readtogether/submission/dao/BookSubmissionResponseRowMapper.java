package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;

import com.github.sveldevorls.readtogether.submission.dto.AuthorLink;
import com.github.sveldevorls.readtogether.submission.dto.GenreLink;

public class BookSubmissionResponseRowMapper implements RowMapper<BookSubmissionResponse> {

    private final List<AuthorLink> authors;
    private final List<GenreLink> genres;

    public BookSubmissionResponseRowMapper(List<AuthorLink> authors, List<GenreLink> genres) {
        this.authors = authors;
        this.genres = genres;
    }

    public BookSubmissionResponse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapRow'");
    }

}
