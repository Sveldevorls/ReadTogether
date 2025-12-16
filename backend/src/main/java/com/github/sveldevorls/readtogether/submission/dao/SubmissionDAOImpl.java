package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

@Repository
public class SubmissionDAOImpl implements SubmissionDAO {

    public final JdbcTemplate jdbcTemplate;

    public SubmissionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createAuthorSubmission(AuthorSubmission submission) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO author_submissions
                    (submitter_id, submitter_comment, author_name, date_of_birth, date_of_death, author_image_url, biography)
                    VALUES
                    (?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, submission.getSubmitterId());
                ps.setString(2, submission.getSubmitterComment());
                ps.setString(3, submission.getAuthorName());
                ps.setDate(4, Date.valueOf(submission.getDateOfBirth()));
                ps.setDate(5, Date.valueOf(submission.getDateOfDeath()));
                ps.setString(6, submission.getAuthorImageUrl());
                ps.setString(7, submission.getBiography());
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

}
