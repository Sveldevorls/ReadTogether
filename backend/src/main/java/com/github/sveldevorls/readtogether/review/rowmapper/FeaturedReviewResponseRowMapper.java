package com.github.sveldevorls.readtogether.review.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.review.dto.FeaturedReviewResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;
import com.github.sveldevorls.readtogether.user.dto.UserSummary;

import java.util.List;

public class FeaturedReviewResponseRowMapper implements RowMapper<FeaturedReviewResponse> {

    // Todo: multiauthor handing
    public FeaturedReviewResponse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        AuthorSummary author = new AuthorSummary(
                rs.getInt("author_id"),
                rs.getString("author_slug"),
                rs.getString("author_name"));

        BookSummary book = new BookSummary(
                List.of(author),
                rs.getInt("book_id"),
                rs.getString("book_slug"),
                rs.getString("title"),
                rs.getString("cover_url"));

        UserSummary reviewer = new UserSummary(
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getString("avatar_url"));

        ReviewSummary content = new ReviewSummary(
                rs.getInt("review_id"),
                parseTimestamp(rs.getTimestamp("created_at")),
                parseTimestamp(rs.getTimestamp("updated_at")),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getInt("like_count"),
                rs.getBoolean("is_featured"));

        return new FeaturedReviewResponse(
                book,
                reviewer,
                content);
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }
}
