package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;
import com.github.sveldevorls.readtogether.submission.entity.ReviewStatus;

public class AuthorSubmissionResponseRowMapper implements RowMapper<AuthorSubmissionResponse> {

    @Override
    public AuthorSubmissionResponse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        AuthorData data = new AuthorData();
        data.setAuthorName(rs.getString("author_name"));
        data.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
        data.setDateOfDeath(rs.getDate("date_of_death").toLocalDate());
        data.setAuthorImageUrl(rs.getString("author_image_url"));
        data.setBiography(rs.getString("biography"));

        AuthorSubmissionResponse response = new AuthorSubmissionResponse(
            // Meta
            rs.getInt("id"),
            parseTimestamp(rs.getTimestamp("created_at")),
            parseTimestamp(rs.getTimestamp("updated_at")),

            // Submission data
            (Integer) rs.getObject("previous_submission_id"),
            rs.getInt("author_id"),
            rs.getString("submitter_username"),
            rs.getString("submitter_comment"),
            rs.getString("reviewer_username"),
            rs.getString("reviewer_comment"),
            parseTimestamp(rs.getTimestamp("reviewed_at")),
            ReviewStatus.valueOf(rs.getString("review_status")),

            // Author data
            data
        );
        
        return response;
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }
}
