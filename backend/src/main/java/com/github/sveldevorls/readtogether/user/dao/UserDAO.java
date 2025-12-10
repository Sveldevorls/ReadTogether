package com.github.sveldevorls.readtogether.user.dao;

import java.util.Optional;

import com.github.sveldevorls.readtogether.user.entity.User;

public interface UserDAO {

    // C //
    void createAdmin(User newUser);

    Optional<User> createUser(User newUser);

    // R //
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<String> getPasswordHashByIdentifier(String identifier);

    Optional<User> getUserById(int id);

    Optional<User> getUserByIdentifier(String identifier);

    Optional<User> getUserByUsername(String username);

    // U //
    void updateUser(User newUserData);
}
