package com.github.sveldevorls.readtogether.user.dao;

import com.github.sveldevorls.readtogether.user.dto.UserDTO;
import com.github.sveldevorls.readtogether.user.entity.User;

public interface UserDAO {

    // C
    void createUser(User newUser);
    
    // R
    UserDTO getUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // U
    void updateUser(User newUserData);
}
