package com.github.sveldevorls.readtogether.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.auth.exception.DuplicateUserException;
import com.github.sveldevorls.readtogether.common.exception.InternalServerErrorException;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.user.dao.UserDAO;
import com.github.sveldevorls.readtogether.user.dto.AdminCreationDTO;
import com.github.sveldevorls.readtogether.user.dto.UserProfileDTO;
import com.github.sveldevorls.readtogether.user.entity.User;

@Service
public class UserService {
    
    public final UserDAO userDao;
    public final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void createAdminIfNotExists(AdminCreationDTO dto) {
        String hashedPassword = encoder.encode(dto.password());
        if (!userDao.existsByUsername(dto.username())) {
            userDao.createAdmin(User.createAdmin(dto.username(), dto.email(), hashedPassword));
        }
    }

    public User createUser(RegisterRequestDTO dto) {
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
        User createdUser = userDao.createUser(
            User.createUser(dto.username(), dto.email(), hashedPassword)
        ).orElseThrow(() -> new InternalServerErrorException());

        return createdUser;
    }

    public Optional<String> getPasswordHashByIdentifier(String identifier) {
        return userDao.getPasswordHashByIdentifier(identifier);
    }

    public User getUserByIdentifier(String identifier) {
        return userDao.getUserByIdentifier(identifier)
                      .orElseThrow(() -> new ResourceNotFoundException());
    }

    public UserProfileDTO getUserProfileData(String username) {
        User user = userDao.getUserByUsername(username)
                           .orElseThrow(() -> new ResourceNotFoundException());
        return new UserProfileDTO(
            user.username(), 
            user.displayName(), 
            user.avatarUrl(), 
            user.bio(), 
            user.createdAt(), 
            user.userRole().name()
        );
    }
}
