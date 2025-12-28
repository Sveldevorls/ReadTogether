package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;
import com.github.sveldevorls.readtogether.submission.rowmapper.AuthorSubmissionResponseRowMapper;

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
    public boolean isInitialized() {
        String sql = "SELECT COUNT(*) FROM author_submissions;";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }

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
    // Return: rows affected
    public int updateReviewById(int submissionId, ReviewStatus status, int reviewerId, String reviewerComment) {
        Instant now = Instant.now();
        String sql = """
                UPDATE author_submissions
                SET reviewer_id = ?,
                    reviewer_comment = ?,
                    reviewed_at = ?,
                    review_status = ?
                WHERE id = ? AND review_status = 'pending'
                """;
        int rows = jdbcTemplate.update(
                sql,
                reviewerId,
                reviewerComment,
                Timestamp.from(now),
                status.name(),
                submissionId);

        return rows;
    }

    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
