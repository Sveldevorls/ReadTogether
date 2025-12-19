package com.github.sveldevorls.readtogether.genres.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.genres.entity.Genre;

public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(@NonNull ResultSet rs, int rowNUms) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setSlug(rs.getString("slug"));
        genre.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        genre.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        genre.setGenreName(rs.getString("genre_name"));

        return genre;
    }

}
