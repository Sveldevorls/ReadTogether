package com.github.sveldevorls.readtogether.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.RegisterRequest;
import com.github.sveldevorls.readtogether.auth.exception.DuplicateUserException;
import com.github.sveldevorls.readtogether.common.exception.InternalServerErrorException;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.user.dao.UserDao;
import com.github.sveldevorls.readtogether.user.dto.UserDataResponse;
import com.github.sveldevorls.readtogether.user.entity.User;

@Service
public class UserService {

    public final UserDao userDao;
    public final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createAdmin(String username, String email, String password) {
        String hashedPassword = encoder.encode(password);
        User admin = User.createAdmin(username, email, hashedPassword);
        userDao.createAdmin(admin);
    }

    public User createUser(RegisterRequest request) {
        List<String> errorFields = new ArrayList<>();
        if (userDao.existsByUsername(request.username())) {
            errorFields.add("username");
        }
        if (userDao.existsByEmail(request.email())) {
            errorFields.add("email");
        }

        if (errorFields.size() > 0) {
            throw new DuplicateUserException(errorFields);
        }

        String hashedPassword = encoder.encode(request.password());
        User createdUser = userDao.createUser(
                User.createUser(request.username(), request.email(), hashedPassword))
                .orElseThrow(() -> new InternalServerErrorException());

        return createdUser;
    }

    public Optional<String> getPasswordHashByIdentifier(String identifier) {
        return userDao.getPasswordHashByIdentifier(identifier);
    }

    public User getUserByIdentifier(String identifier) {
        return userDao.getUserByIdentifier(identifier)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    // Todo: Change return type to UserProfileResponse once books and authors are
    // added
    public UserDataResponse getUserProfileData(String username) {
        User user = userDao.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException());
        return new UserDataResponse(
                user.getUsername(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                user.getBio(),
                user.getCreatedAt(),
                user.getUserRole().name());
    }

    // Todo: handle error
    public String updateBio(String username, String newBio) {
        userDao.updateBio(username, newBio);
        User updatedUser = userDao.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException());
        return updatedUser.getBio();
    }

    // Todo: handle error
    public String updateDisplayName(String username, String newDisplayName) {
        userDao.updateDisplayName(username, newDisplayName);
        User updatedUser = userDao.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException());
        return updatedUser.getDisplayName();
    }

    public String updateAvatarUrl(String username, String newAvatarUrl) {
        userDao.updateAvatarUrl(username, newAvatarUrl);
        User updatedUser = userDao.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException());
        return updatedUser.getDisplayName();
    }

    public boolean isInitialized() {
        return userDao.isInitialized();
    }
}
