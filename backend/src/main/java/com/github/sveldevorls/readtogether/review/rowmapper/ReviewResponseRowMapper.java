package com.github.sveldevorls.readtogether.review.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.review.dto.ReviewResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewSummary;
import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public class ReviewResponseRowMapper implements RowMapper<ReviewResponse> {

    public ReviewResponse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        UserSummary userSummary = new UserSummary(
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getString("avatar_url"));

        ReviewSummary reviewSummary = new ReviewSummary(
                rs.getInt("id"),
                parseTimestamp(rs.getTimestamp("created_at")),
                parseTimestamp(rs.getTimestamp("updated_at")),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getInt("like_count"),
                rs.getBoolean("is_featured"));

        return new ReviewResponse(userSummary, reviewSummary);
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }
}
