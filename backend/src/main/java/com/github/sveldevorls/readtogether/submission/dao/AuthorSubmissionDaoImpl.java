package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

@Repository
public class AuthorSubmissionDaoImpl implements AuthorSubmissionDao {

    public final JdbcTemplate jdbcTemplate;

    public AuthorSubmissionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
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
                keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create author submission");
        }
        int generatedId = key.intValue();

        return generatedId;
    }

    // R
    public Optional<AuthorSubmissionResponse> getSubmissionResponseById(int id) {
        String sql = """
                    SELECT a.*, s.username AS "submitter_username", r.username AS "reviewer_username"
                    FROM author_submissions a
                    JOIN users s ON a.submitter_id = s.id
                    LEFT JOIN users r ON a.reviewer_id = r.id
                    WHERE a.id = ?;
                """;

        List<AuthorSubmissionResponse> result = jdbcTemplate.query(
                sql,
                new AuthorSubmissionResponseRowMapper(),
                id);

        return result.stream().findFirst();
    }

    /*
     * public Optional<AuthorSubmission> getSubmissionEntityById(int id) {
     * String sql = "SELECT * FROM author_submissions WHERE id = ?;";
     * List<AuthorSubmission> result = jdbcTemplate.query(
     * sql,
     * new AuthorSubmissionEntityMapper(),
     * id);
     * 
     * return result.stream().findFirst();
     * }
     */

    public Optional<Integer> getAuthorIdById(int id) {
        String sql = "SELECT author_id FROM author_submissions WHERE id = ?";
        List<Integer> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getInt("author_id"),
                id);
        return result.stream().findFirst();
    }

    // U
    public void updateReviewStatusById(int id, String status) {
        String sql = "UPDATE author_submissions SET review_status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    @Override
    public void updateReviewerCommentById(int id, String reviewerComment) {
        String sql = "UPDATE author_submissions SET reviewer_comment = ? WHERE id = ?";
        jdbcTemplate.update(sql, reviewerComment, id);
    }

    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
