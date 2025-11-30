package com.github.sveldevorls.readtogether.user;

public class UserService {
    
    public final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void registerUser(UserDTO newUserDTO) {
        userDao.createUser(null);
    }
}
