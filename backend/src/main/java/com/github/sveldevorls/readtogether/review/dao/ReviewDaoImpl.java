package com.github.sveldevorls.readtogether.review.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.book.rowmapper.BookRatingsResponseRowMapper;
import com.github.sveldevorls.readtogether.review.dto.RatingsSummary;
import com.github.sveldevorls.readtogether.review.dto.ReviewResponse;
import com.github.sveldevorls.readtogether.review.dto.ReviewSummary;
import com.github.sveldevorls.readtogether.review.entity.Review;
import com.github.sveldevorls.readtogether.review.rowmapper.ReviewResponseRowMapper;
import com.github.sveldevorls.readtogether.review.rowmapper.ReviewSummaryRowMapper;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    public final JdbcTemplate jdbcTemplate;

    public ReviewDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
    public int createReview(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO reviews
                    (user_id, book_id, rating, comment)
                    VALUES
                    (?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, review.getUserId());
                    ps.setInt(2, review.getBookId());
                    ps.setInt(3, review.getRating());
                    ps.setString(4, review.getComment());
                    return ps;
                },
                keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create review");
        }
        int generatedId = key.intValue();

        return generatedId;
    }

    // R
    public Optional<ReviewSummary> getReviewSummaryById(int id) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        List<ReviewSummary> result = jdbcTemplate.query(
                sql,
                new ReviewSummaryRowMapper(),
                id);
        return result.stream().findFirst();
    }

    public Optional<ReviewSummary> getUserBookReview(int userId, int bookId) {
        String sql = "SELECT * FROM reviews WHERE user_id = ? AND book_id = ?";
        List<ReviewSummary> result = jdbcTemplate.query(
                sql,
                new ReviewSummaryRowMapper(),
                userId,
                bookId);
        return result.stream().findFirst();
    }

    public Optional<RatingsSummary> getBookRatingsResponse(int id) {
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
        List<RatingsSummary> result = jdbcTemplate.query(
                sql,
                new BookRatingsResponseRowMapper(),
                id);
        return result.stream().findFirst();
    }

    public List<ReviewResponse> getCommunityReviewsByBookId(int bookId, Integer userId) {
        String sql = """
                SELECT u.username, u.display_name, u.avatar_url, r.*
                FROM reviews AS r
                JOIN users AS u
                ON r.user_id = u.id
                WHERE r.book_id = ?
                """;
        List<ReviewResponse> result;

        if (userId == null) {
            result = jdbcTemplate.query(
                    sql,
                    new ReviewResponseRowMapper(),
                    bookId);
        } else {
            sql += "AND r.user_id != ?";
            result = jdbcTemplate.query(
                    sql,
                    new ReviewResponseRowMapper(),
                    bookId,
                    userId);
        }

        return result;
    }
}
