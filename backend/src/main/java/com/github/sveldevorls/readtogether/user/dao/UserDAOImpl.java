package com.github.sveldevorls.readtogether.user.dao;

import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.common.exception.InternalServerErrorException;
import com.github.sveldevorls.readtogether.user.entity.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UserDAOImpl implements UserDAO {

    public final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C //
    public void createAdmin(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, user_role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
            sql, 
            user.username(), user.email(), user.passwordHash(), user.userRole()
        );
    }

    public User createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.username());
                ps.setString(2, user.email());
                ps.setString(3, user.passwordHash());
                return ps;
            },
            keyHolder
        );
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new InternalServerErrorException();
        }
        int generatedId = key.intValue();
        return getUserById(generatedId);
    }

    // R //
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

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

    public User getUserById(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User resultUser = jdbcTemplate.queryForObject(
                    sql,
                    new UserRowMapper(),
                    id);
            return resultUser;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public Optional<User> getUserByIdentifier(String identifier) {
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
        List<User> result = jdbcTemplate.query(
                    sql,
                    new UserRowMapper(),
                    identifier,
                    identifier);
        
        return result.stream().findFirst();
    }

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

    // U //
    public void updateUser(User user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUserByUsername'");
    }
}
