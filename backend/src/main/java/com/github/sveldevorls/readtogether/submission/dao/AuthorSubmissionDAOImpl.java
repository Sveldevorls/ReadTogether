package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

@Repository
public class AuthorSubmissionDAOImpl implements AuthorSubmissionDAO {

    public final JdbcTemplate jdbcTemplate;

    public AuthorSubmissionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createAuthorSubmission(AuthorSubmission submission) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO author_submissions
                    (author_id, submitter_id, submitter_comment, author_name, date_of_birth, date_of_death, author_image_url, biography)
                    VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, submission.getAuthorId());
                ps.setInt(2, submission.getSubmitterId());
                ps.setString(3, submission.getSubmitterComment());
                ps.setString(4, submission.getAuthorData().getAuthorName());
                ps.setDate(5, parseNullableDate(submission.getAuthorData().getDateOfBirth()));
                ps.setDate(6, parseNullableDate(submission.getAuthorData().getDateOfDeath()));
                ps.setString(7, submission.getAuthorData().getAuthorImageUrl());
                ps.setString(8, submission.getAuthorData().getBiography());
                return ps;
            },
            keyHolder
        );

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create author submission");
        }
        int generatedId = key.intValue();

        return generatedId;
    }

    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
