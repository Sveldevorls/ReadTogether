package com.github.sveldevorls.readtogether.user.dao;

import java.util.Optional;

import com.github.sveldevorls.readtogether.user.entity.User;

public interface UserDAO {

    // C //
    void createAdmin(User newUser);

    User createUser(User newUser);

    // R //
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    String getPasswordHashByIdentifier(String identifier);

    User getUserById(int id);

    Optional<User> getUserByIdentifier(String identifier);

    User getUserByUsername(String username);

    // U //
    void updateUser(User newUserData);
}
