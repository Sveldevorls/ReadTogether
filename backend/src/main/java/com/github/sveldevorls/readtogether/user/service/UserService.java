package com.github.sveldevorls.readtogether.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.auth.exception.DuplicateUserException;
import com.github.sveldevorls.readtogether.user.dao.UserDAO;
import com.github.sveldevorls.readtogether.user.entity.User;

@Service
public class UserService {
    
    public final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void createUser(RegisterRequestDTO dto) {
        List<String> errorFields = new ArrayList<>();
        if (userDao.existsByUsername(dto.username())) {
            errorFields.add("username");
        }
        if (userDao.existsByEmail(dto.email())) {
            errorFields.add("email");
        }

        if (errorFields.size() > 0) {
            throw new DuplicateUserException(errorFields);
        }

        Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        String hashedPassword = encoder.encode(dto.password());
        userDao.createUser(
            User.createUser(dto.username(), dto.email(), hashedPassword)
        );
    }
}
