package com.github.sveldevorls.readtogether.book.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.book.entity.Book;

@Repository
public class BookDaoImpl implements BookDao {

    public final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO books
                    (slug, title, isbn, book_description, publisher_name, published_date, cover_url)
                    VALUES
                    (?, ?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, book.getSlug());
                    ps.setString(2, book.getBookData().getTitle());
                    ps.setString(3, book.getBookData().getIsbn());
                    ps.setString(4, book.getBookData().getBookDescription());
                    ps.setString(5, book.getBookData().getPublisherName());
                    ps.setDate(6, parseNullableDate(book.getBookData().getPublishedDate()));
                    ps.setString(7, book.getBookData().getCoverUrl());
                    return ps;
                },
                keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create book");
        }
        int generatedId = key.intValue();

        return generatedId;
    }

    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
