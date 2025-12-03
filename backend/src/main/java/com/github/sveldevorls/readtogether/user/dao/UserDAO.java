package com.github.sveldevorls.readtogether.user.dao;

import com.github.sveldevorls.readtogether.user.entity.User;

public interface UserDAO {

    // C
    void createUser(User newUser);
    
    // R
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // U
    void updateUser(User newUserData);
}
