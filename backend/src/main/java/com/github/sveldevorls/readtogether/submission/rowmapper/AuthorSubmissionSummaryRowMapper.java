package com.github.sveldevorls.readtogether.submission.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;
import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public class AuthorSubmissionSummaryRowMapper implements RowMapper<AuthorSubmissionSummary> {

    public AuthorSubmissionSummary mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        UserSummary submitter = new UserSummary(
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getString("avatar_url"));
        AuthorSummary author = new AuthorSummary(
                rs.getInt("author_id"),
                rs.getString("slug"),
                rs.getString("author_name"));
        AuthorSubmissionSummary summary = new AuthorSubmissionSummary(
                submitter,
                author,
                rs.getInt("submission_id"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant(),
                rs.getString("review_status"),
                rs.getString("submission_type"));
        return summary;
    }

}
