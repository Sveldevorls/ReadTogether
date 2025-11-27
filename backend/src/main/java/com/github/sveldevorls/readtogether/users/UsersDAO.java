package com.github.sveldevorls.readtogether.users;

public interface UsersDAO {

    // C
    public void addNewUser();
    
    // R
    public User getUserByUsername(String username);
    
    // U
    public void updateUserByUsername(String username, User newUserData);
}
