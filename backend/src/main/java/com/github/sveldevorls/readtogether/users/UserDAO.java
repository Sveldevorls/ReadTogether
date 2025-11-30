package com.github.sveldevorls.readtogether.users;

public interface UserDAO {

    // C
    void createUser(UserEntity newUser);
    
    // R
    UserDTO getUserByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    // U
    void updateUser(UserEntity newUserData);
}
