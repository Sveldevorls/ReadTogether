package com.github.sveldevorls.readtogether.submission.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.book.entity.BookData;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;

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

    // Util
    public Date parseNullableDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

}
