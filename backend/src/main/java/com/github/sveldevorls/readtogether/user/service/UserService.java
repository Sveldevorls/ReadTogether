package com.github.sveldevorls.readtogether.user.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.register.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.user.dao.UserDAO;
import com.github.sveldevorls.readtogether.user.entity.User;

@Service
public class UserService {
    
    public final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void createUser(RegisterRequestDTO dto) {
        Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        String hashedPassword = encoder.encode(dto.password());
        userDao.createUser(
            User.createUser(dto.username(), dto.email(), hashedPassword)
        );
    }
}
