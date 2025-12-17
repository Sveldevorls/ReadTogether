package com.github.sveldevorls.readtogether.user.dao;

import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.user.entity.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UserDaoImpl implements UserDao {

    public final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C //
    public void createAdmin(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, user_role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
            sql, 
            user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getUserRole().name()
        );
    }

    public Optional<User> createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPasswordHash());
                return ps;
            },
            keyHolder
        );
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataRetrievalFailureException("Failed to create user");
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

    public Optional<String> getPasswordHashByIdentifier(String identifier) {
        String sql = "SELECT password_hash FROM users WHERE username = ? OR email = ?";
        List<String> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString(1),
                identifier,
                identifier);
        return result.stream().findFirst();
    }

    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> result = jdbcTemplate.query(
                sql,
                new UserRowMapper(),
                id);
        return result.stream().findFirst();
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

    public Optional<User> getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> result = jdbcTemplate.query(
                sql,
                new UserRowMapper(),
                username);
        return result.stream().findFirst();
    }

    // U //
    public void updateBio(String username, String newBio) {
        String sql = "UPDATE users SET bio = ? WHERE username = ?";
        jdbcTemplate.update(sql, newBio, username);
    }

    public void updateDisplayName(String username, String newDisplayName) {
        String sql = "UPDATE users SET display_name = ? WHERE username = ?";
        jdbcTemplate.update(sql, newDisplayName, username);
    }
}
