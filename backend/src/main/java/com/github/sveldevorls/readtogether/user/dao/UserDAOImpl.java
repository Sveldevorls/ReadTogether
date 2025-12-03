package com.github.sveldevorls.readtogether.user.dao;

import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.user.entity.User;

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
        String sql = "SELECT * FROM users WHERE username = ?";
        User resultUser = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    User returnUser = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("display_name"),
                        rs.getString("password_hash"),
                        rs.getString("avatar_url"),
                        rs.getString("bio"),
                        rs.getTimestamp("created_at").toString(), //fix
                        rs.getTimestamp("updated_at").toString(), //fix
                        rs.getString("user_role")
                    );
                    return returnUser;
                },
                username
            );
        return resultUser;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        User resultUser = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    User returnUser = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("display_name"),
                        rs.getString("password_hash"),
                        rs.getString("avatar_url"),
                        rs.getString("bio"),
                        rs.getTimestamp("created_at").toString(), //fix
                        rs.getTimestamp("updated_at").toString(), //fix
                        rs.getString("user_role")
                    );
                    return returnUser;
                },
                email
            );
        return resultUser;
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
