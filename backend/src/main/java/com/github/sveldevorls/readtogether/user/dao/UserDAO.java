package com.github.sveldevorls.readtogether.user.dao;

import com.github.sveldevorls.readtogether.user.entity.User;

public interface UserDAO {

    // C //
    void createUser(User newUser);

    // R //
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    String getPasswordHashByIdentifier(String identifier);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    // U //
    void updateUser(User newUserData);
}
