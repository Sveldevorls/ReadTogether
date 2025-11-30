package com.github.sveldevorls.readtogether.user.dao;

import org.springframework.stereotype.Repository;

import com.github.sveldevorls.readtogether.user.dto.UserDTO;
import com.github.sveldevorls.readtogether.user.entity.User;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserDAOImpl implements UserDAO {

    public final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // C
    public void createUser(User newUser) {
        throw new UnsupportedOperationException("Unimplemented method 'getUserByUsername'");
    }

    // R
    public UserDTO getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        UserDTO resultUserDTO = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    UserDTO returnUserDTO = new UserDTO(
                            rs.getString("username"),
                            rs.getString("display_name"),
                            rs.getString("avatar_url"),
                            rs.getString("bio"),
                            rs.getString("created_at"),
                            rs.getString("user_role"));
                    return returnUserDTO;
                },
                username);

        return resultUserDTO;
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
    public void updateUser(User newUserData) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUserByUsername'");
    }
}
