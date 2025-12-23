package com.github.sveldevorls.readtogether.submission.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

public class BookSubmissionResponseRowMapper implements RowMapper<BookSubmissionResponse> {

    private final List<AuthorSummary> authors;
    private final List<GenreSummary> genres;

    public BookSubmissionResponseRowMapper(List<AuthorSummary> authors, List<GenreSummary> genres) {
        this.authors = authors;
        this.genres = genres;
    }

    public BookSubmissionResponse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        BookData data = new BookData();
        data.setTitle(rs.getString("title"));
        data.setIsbn(rs.getString("isbn"));
        data.setBookDescription(rs.getString("book_description"));
        data.setPublisherName(rs.getString("publisher_name"));
        data.setPublishedDate(parseDate(rs.getDate("published_date")));
        data.setCoverUrl(rs.getString("cover_url"));

        return new BookSubmissionResponse(
                // Meta
                rs.getInt("id"),
                parseTimestamp(rs.getTimestamp("created_at")),
                parseTimestamp(rs.getTimestamp("updated_at")),

                // Submission data
                (Integer) rs.getObject("previous_submission_id"),
                rs.getInt("book_id"),
                rs.getString("submitter_username"),
                rs.getString("submitter_comment"),
                rs.getString("reviewer_username"),
                rs.getString("reviewer_comment"),
                parseTimestamp(rs.getTimestamp("reviewed_at")),
                ReviewStatus.valueOf(rs.getString("review_status")),

                // Author data
                data,

                // List of authors
                authors,

                // List of genres
                genres);
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }

    public LocalDate parseDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
