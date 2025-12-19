package com.github.sveldevorls.readtogether.genres.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.genres.entity.Genre;

@Repository
public class GenreDaoImpl implements GenreDao {

    public final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM genres";
        List<Genre> result = jdbcTemplate.query(sql, new GenreRowMapper());
        return result;
    }

}
