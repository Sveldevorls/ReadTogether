package com.github.sveldevorls.readtogether.book.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.book.dto.BookRatingsResponse;
import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.book.rowmapper.BookRatingsResponseRowMapper;
import com.github.sveldevorls.readtogether.book.rowmapper.BookRowMapper;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.genres.dto.GenreSummary;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSummary;

@Repository
public class BookDaoImpl implements BookDao {

    public final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
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

    public void mapBookAuthor(int bookId, int authorId) {
        String sql = "INSERT INTO book_author_map VALUES (?, ?)";
        jdbcTemplate.update(sql, bookId, authorId);
    }

    public void mapBookGenre(int bookId, int genreId) {
        String sql = "INSERT INTO book_genre_map VALUES (?, ?)";
        jdbcTemplate.update(sql, bookId, genreId);
    }

    // R
    public Optional<Book> getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        List<Book> result = jdbcTemplate.query(
                sql,
                new BookRowMapper(),
                id);
        return result.stream().findFirst();
    }

    public List<AuthorSummary> getAuthorSummariesById(int id) {
        String sql = """
                SELECT a.id, a.slug, a.author_name
                FROM authors AS a
                JOIN book_author_map AS m
                ON m.author_id = a.id
                WHERE m.book_id = ?;
                """;
        List<AuthorSummary> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    return new AuthorSummary(
                            rs.getInt("id"),
                            rs.getString("slug"),
                            rs.getString("author_name"));
                },
                id);
        return result;
    }

    public List<GenreSummary> getGenreSummariesById(int id) {
        String sql = """
                SELECT g.id, g.slug, g.genre_name
                FROM book_genre_map AS m
                JOIN genres AS g
                ON m.genre_id = g.id
                WHERE m.book_id = ?
                """;
        List<GenreSummary> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    return new GenreSummary(
                            rs.getInt("id"),
                            rs.getString("slug"),
                            rs.getString("genre_name"));
                },
                id);
        return result;
    }

    // U
    public int updateReviewStatusById(int id, ReviewStatus status) {
        String sql = "UPDATE books SET review_status = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, status.name(), id);
        return rows;
    }

    // Util
    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    @Override
    public Optional<BookRatingsResponse> getBookRatingResponse(int id) {
        String sql = """
                SELECT
                    COUNT(rating) AS total,
                    AVG(rating) AS average,
                    COUNT(CASE WHEN rating = 1 THEN 1 END) AS ones,
                    COUNT(CASE WHEN rating = 2 THEN 1 END) AS twos,
                    COUNT(CASE WHEN rating = 3 THEN 1 END) AS threes,
                    COUNT(CASE WHEN rating = 4 THEN 1 END) AS fours,
                    COUNT(CASE WHEN rating = 5 THEN 1 END) AS fives
                FROM reviews
                WHERE book_id = ?
                """;
        List<BookRatingsResponse> result = jdbcTemplate.query(
                sql,
                new BookRatingsResponseRowMapper(),
                id);
        return result.stream().findFirst();
    }
}
