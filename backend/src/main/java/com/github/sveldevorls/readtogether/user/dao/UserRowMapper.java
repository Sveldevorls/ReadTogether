package com.github.sveldevorls.readtogether.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.user.entity.Role;
import com.github.sveldevorls.readtogether.user.entity.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        User returnUser = new User(
                rs.getInt("id"),
                rs.getTimestamp("created_at").toString(), // fix
                rs.getTimestamp("updated_at").toString(), // fix
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("display_name"),
                rs.getString("password_hash"),
                rs.getString("avatar_url"),
                rs.getString("bio"),
                Role.valueOf(rs.getString("user_role")));
        return returnUser;
    }
}
