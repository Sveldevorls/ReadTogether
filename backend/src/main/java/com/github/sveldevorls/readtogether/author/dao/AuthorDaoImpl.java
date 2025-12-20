package com.github.sveldevorls.readtogether.author.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    public final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
    public int createAuthor(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO authors
                    (slug, author_name, date_of_birth, date_of_death, author_image_url, biography)
                    VALUES
                    (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, author.getSlug());
                    ps.setString(2, author.getAuthorData().getAuthorName());
                    ps.setDate(3, parseNullableDate(author.getAuthorData().getDateOfBirth()));
                    ps.setDate(4, parseNullableDate(author.getAuthorData().getDateOfDeath()));
                    ps.setString(5, author.getAuthorData().getAuthorImageUrl());
                    ps.setString(6, author.getAuthorData().getBiography());
                    return ps;
                },
                keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create author");
        }
        int generatedId = key.intValue();

        return generatedId;
    }

    // R
    public List<Author> searchApprovedAuthorsByName(String name) {
        String sql = """
                SELECT * FROM authors WHERE LOWER(author_name) LIKE ? AND review_status = 'Approved'
                """;
        List<Author> result = jdbcTemplate.query(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, "%" + name + "%");
                    return ps;
                },
                new AuthorRowMapper());
        return result;
    }

    // U
    public int updateReviewStatusById(int id, ReviewStatus status) {
        String sql = "UPDATE authors SET review_status = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, status.name(), id);
        return rows;
    }

    /*
     * // D
     * public int deleteById(int id) {
     * String sql = "DELETE FROM authors WHERE id = ?";
     * int rows = jdbcTemplate.update(sql, id);
     * return rows;
     * }
     */

    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
