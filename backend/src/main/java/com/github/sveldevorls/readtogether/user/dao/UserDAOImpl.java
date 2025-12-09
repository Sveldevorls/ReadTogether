package com.github.sveldevorls.readtogether.user.dao;

import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.user.entity.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserDAOImpl implements UserDAO {

    public final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.username(), user.email(), user.passwordHash());
    }

    // R
    public User getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            User resultUser = jdbcTemplate.queryForObject(
                    sql,
                    new UserRowMapper(),
                    username);
            return resultUser;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User resultUser = jdbcTemplate.queryForObject(
                sql,
                new UserRowMapper(),
                email);
        return resultUser;
    }

    // Returns null if identifier does not exist
    public String getPasswordHashByIdentifier(String identifier) {
        try {
            String sql = "SELECT password_hash FROM users WHERE username = ? OR email = ?";
            String resultHash = jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> rs.getString(1),
                    identifier,
                    identifier);
            return resultHash;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    // U
    public void updateUser(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUserByUsername'");
    }
}
