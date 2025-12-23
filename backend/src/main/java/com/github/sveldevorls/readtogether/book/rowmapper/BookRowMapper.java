package com.github.sveldevorls.readtogether.book.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public class BookRowMapper implements RowMapper<Book> {

    public Book mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        BookData data = new BookData();
        data.setTitle(rs.getString("title"));
        data.setIsbn(rs.getString("isbn"));
        data.setBookDescription(rs.getString("book_description"));
        data.setPublisherName(rs.getString("publisher_name"));
        data.setPublishedDate(parseDate(rs.getDate("published_date")));
        data.setCoverUrl(rs.getString("cover_url"));

        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setSlug(rs.getString("slug"));
        book.setCreatedAt(parseTimestamp(rs.getTimestamp("created_at")));
        book.setUpdatedAt(parseTimestamp(rs.getTimestamp("updated_at")));
        book.setReviewStatus(ReviewStatus.valueOf(rs.getString("review_status")));

        book.setBookData(data);
        return book;
    }

    public LocalDate parseDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }
}
