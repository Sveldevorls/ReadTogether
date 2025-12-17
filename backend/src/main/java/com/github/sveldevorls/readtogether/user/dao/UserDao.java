package com.github.sveldevorls.readtogether.user.dao;

import java.util.Optional;

import com.github.sveldevorls.readtogether.user.entity.User;

public interface UserDao {

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
    void updateBio(String username, String newBio);

    void updateDisplayName(String username, String newDisplayName);
}
