package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

public class AuthorSubmissionEntityMapper implements RowMapper<AuthorSubmission> {

    @Override
    public AuthorSubmission mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        AuthorData data = new AuthorData();
        data.setAuthorName(rs.getString("author_name"));
        data.setDateOfBirth(parseDate(rs.getDate("date_of_birth")));
        data.setDateOfDeath(parseDate(rs.getDate("date_of_death")));
        data.setAuthorImageUrl(rs.getString("author_image_url"));
        data.setBiography(rs.getString("biography"));

        AuthorSubmission submission = new AuthorSubmission();

        // Meta
        submission.setId(rs.getInt("id"));
        submission.setCreatedAt(parseTimestamp(rs.getTimestamp("created_at")));
        submission.setUpdatedAt(parseTimestamp(rs.getTimestamp("updated_at")));

        // Submission data
        submission.setPreviousSubmissionId((Integer) rs.getObject("previous_submission_id"));
        submission.setAuthorId(rs.getInt("author_id"));
        submission.setSubmitterId(rs.getInt("submitter_id"));
        submission.setSubmitterComment(rs.getString("submitter_comment"));
        submission.setReviewerId(rs.getInt("reviewer_id"));
        submission.setReviewerComment(rs.getString("reviewer_comment"));
        submission.setReviewedAt(parseTimestamp(rs.getTimestamp("reviewed_at")));
        submission.setReviewStatus(ReviewStatus.valueOf(rs.getString("review_status")));

        // Author data
        submission.setAuthorData(data);

        return submission;
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }

    public LocalDate parseDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
