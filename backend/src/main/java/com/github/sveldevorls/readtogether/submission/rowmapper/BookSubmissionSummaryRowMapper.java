package com.github.sveldevorls.readtogether.submission.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;
import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionSummary;
import com.github.sveldevorls.readtogether.user.dto.UserSummary;

public class BookSubmissionSummaryRowMapper implements RowMapper<BookSubmissionSummary> {

    @Override
    public BookSubmissionSummary mapRow(ResultSet rs, int arg1) throws SQLException {
        UserSummary submitter = new UserSummary(
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getString("avatar_url"));
        BookSummary book = new BookSummary(
                null,
                rs.getInt("book_id"),
                rs.getString("slug"),
                rs.getString("title"),
                null);
        BookSubmissionSummary summary = new BookSubmissionSummary(
                submitter,
                book,
                rs.getInt("submission_id"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("updated_at").toInstant(),
                rs.getString("review_status"),
                rs.getString("submission_type"));
        return summary;
    }

}
