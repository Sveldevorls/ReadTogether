package com.github.sveldevorls.readtogether.book.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.book.dto.BookSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

public class BookSummaryRowMapper implements RowMapper<BookSummary> {

    public BookSummary mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        AuthorSummary author = new AuthorSummary(
                rs.getInt("author_id"),
                rs.getString("author_slug"),
                rs.getString("author_name"));

        BookSummary book = new BookSummary(
                List.of(author),
                rs.getInt("book_id"),
                rs.getString("slug"),
                rs.getString("title"),
                rs.getString("cover_url"));
        
        return book;
    }

}
