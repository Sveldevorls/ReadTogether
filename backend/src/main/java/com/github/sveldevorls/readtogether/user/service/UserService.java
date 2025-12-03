package com.github.sveldevorls.readtogether.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.LoginRequestDTO;
import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.auth.exception.DuplicateUserException;
import com.github.sveldevorls.readtogether.auth.exception.InvalidLoginCredentialsException;
import com.github.sveldevorls.readtogether.user.dao.UserDAO;
import com.github.sveldevorls.readtogether.user.entity.User;

@Service
public class UserService {
    
    public final UserDAO userDao;
    public final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

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

        String hashedPassword = encoder.encode(dto.password());
        userDao.createUser(
            User.createUser(dto.username(), dto.email(), hashedPassword)
        );
    }

    // move to auth service later
    public void login(LoginRequestDTO dto) {
        User currentUser = null;
        String dummyHash = "$argon2id$v=19$m=16384,t=2,p=1$f6Rkj64oxISvfN2Z2r27Jg$RH7XNkVCi8GQKaPyPrbuKqVRTRRQfiVa4dUow4KOScI";

        if (userDao.existsByUsername(dto.identifier())) {
            currentUser = userDao.getUserByUsername(dto.identifier());
            dummyHash = currentUser.passwordHash();
        }
        else if (userDao.existsByEmail(dto.identifier())) {
            currentUser = userDao.getUserByEmail(dto.identifier());
            dummyHash = currentUser.passwordHash();
        }

        if (!encoder.matches(dto.password(), dummyHash)) {
            throw new InvalidLoginCredentialsException();
        }
    }
}
