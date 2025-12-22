package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.GenreLink;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;
import com.github.sveldevorls.readtogether.submission.rowmapper.BookSubmissionResponseRowMapper;
import com.github.sveldevorls.readtogether.submission.dto.AuthorLink;

@Repository
public class BookSubmissionDaoImpl implements BookSubmissionDao {

    public final JdbcTemplate jdbcTemplate;

    public BookSubmissionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
    public int createBookSubmission(BookSubmission submission) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO book_submissions
                    (book_id,
                    submitter_id,
                    submitter_comment,
                    title,
                    isbn,
                    book_description,
                    publisher_name,
                    published_date,
                    cover_url)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        BookData bookData = submission.getBookData();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, submission.getBookId());
                    ps.setInt(2, submission.getSubmitterId());
                    ps.setString(3, submission.getSubmitterComment());
                    ps.setString(4, bookData.getTitle());
                    ps.setString(5, bookData.getIsbn());
                    ps.setString(6, bookData.getBookDescription());
                    ps.setString(7, bookData.getPublisherName());
                    ps.setDate(8, parseNullableDate(bookData.getPublishedDate()));
                    ps.setString(9, bookData.getCoverUrl());
                    return ps;
                },
                keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create book submission");
        }
        int generatedId = key.intValue();

        return generatedId;
    }

    public void mapSubmissionBookAuthor(int submissionId, int bookId, int authorId) {
        String sql = "INSERT INTO book_submission_author_map VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, submissionId, bookId, authorId);
    }

    public void mapSubmissionBookGenre(int submissionId, int bookId, int genreId) {
        String sql = "INSERT INTO book_submission_genre_map VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, submissionId, bookId, genreId);
    }

    // R
    public List<AuthorLink> getAuthorLinksById(int submissionId) {
        String sql = """
            SELECT a.id, a.slug, a.author_name
            FROM book_submission_author_map AS m
            JOIN authors AS a
            ON m.author_id = a.id
            WHERE m.submission_id = ?
            """;
        List<AuthorLink> result = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                return new AuthorLink(
                    rs.getInt("id"),
                    rs.getString("slug"),
                    rs.getString("author_name")
                );
            },
            submissionId);
        return result;
    }

    public Optional<Integer> getBookIdById(int id) {
        String sql = "SELECT book_id FROM book_submissions WHERE id = ?";
        List<Integer> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getInt("book_id"),
                id);
        return result.stream().findFirst();
    }

    public List<GenreLink> getGenreLinksById(int submissionId) {
        String sql = """
            SELECT g.id, g.slug, g.genre_name
            FROM book_submission_genre_map AS m
            JOIN genres AS g
            ON m.genre_id = g.id
            WHERE m.submission_id = ?
            """;
        List<GenreLink> result = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                return new GenreLink(
                    rs.getInt("id"),
                    rs.getString("slug"),
                    rs.getString("genre_name")
                );
            },
            submissionId);
        return result;
    }

    public List<Integer> getMappedAuthorsById(int id) {
        String sql = "SELECT author_id FROM book_submission_author_map WHERE submission_id = ?";
        List<Integer> result = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                return rs.getInt("author_id");
            },
            id
        );
        return result;
    }

    public List<Integer> getMappedGenresById(int id) {
        String sql = "SELECT genre_id FROM book_submission_genre_map WHERE submission_id = ?";
        List<Integer> result = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                return rs.getInt("genre_id");
            },
            id
        );
        return result;
    }

    public Optional<BookSubmissionResponse> getSubmissionResponseById(int id) {
        List<AuthorLink> authors = getAuthorLinksById(id);
        List<GenreLink> genres = getGenreLinksById(id);

        String sql = """
                    SELECT b.*, s.username AS "submitter_username", r.username AS "reviewer_username"
                    FROM book_submissions b
                    JOIN users s ON b.submitter_id = s.id
                    LEFT JOIN users r ON b.reviewer_id = r.id
                    WHERE b.id = ?;
                """;
        List<BookSubmissionResponse> result = jdbcTemplate.query(
                sql,
                new BookSubmissionResponseRowMapper(authors, genres),
                id);

        return result.stream().findFirst();
    }

     // U
    // Return: rows affected
    public int updateReviewById(int submissionId, ReviewStatus status, int reviewerId, String reviewerComment) {
        Instant now = Instant.now();
        String sql = """
                UPDATE book_submissions
                SET reviewer_id = ?,
                    reviewer_comment = ?,
                    reviewed_at = ?,
                    review_status = ?
                WHERE id = ? AND review_status = 'pending'
                """;
        int rows = jdbcTemplate.update(
                sql,
                reviewerId,
                reviewerComment,
                Timestamp.from(now),
                status.name(),
                submissionId);

        return rows;
    }

    // Util
    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
