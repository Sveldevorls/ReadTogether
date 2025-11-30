package com.github.sveldevorls.readtogether.user.service;

import com.github.sveldevorls.readtogether.user.dao.UserDAO;
import com.github.sveldevorls.readtogether.user.dto.UserDTO;

public class UserService {
    
    public final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void registerUser(UserDTO newUserDTO) {
        userDao.createUser(null);
    }
}
