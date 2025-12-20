package com.github.sveldevorls.readtogether.author.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public class AuthorRowMapper implements RowMapper<Author> {

    public Author mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        AuthorData data = new AuthorData();
        data.setAuthorName(rs.getString("author_name"));
        data.setDateOfBirth(parseDate(rs.getDate("date_of_birth")));
        data.setDateOfDeath(parseDate(rs.getDate("date_of_death")));
        data.setAuthorImageUrl(rs.getString("author_image_url"));
        data.setBiography(rs.getString("biography"));

        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setSlug(rs.getString("slug"));
        author.setCreatedAt(parseTimestamp(rs.getTimestamp("created_at")));
        author.setUpdatedAt(parseTimestamp(rs.getTimestamp("updated_at")));
        author.setReviewStatus(ReviewStatus.valueOf(rs.getString("review_status")));
        author.setAuthorData(data);

        return author;
    }

    public Instant parseTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }

    public LocalDate parseDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
