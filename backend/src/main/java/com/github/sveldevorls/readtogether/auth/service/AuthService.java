package com.github.sveldevorls.readtogether.auth.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.LoginRequest;
import com.github.sveldevorls.readtogether.auth.dto.LoginResponse;
import com.github.sveldevorls.readtogether.auth.dto.RegisterRequest;
import com.github.sveldevorls.readtogether.auth.dto.RegisterResponse;
import com.github.sveldevorls.readtogether.auth.exception.InvalidLoginCredentialsException;
import com.github.sveldevorls.readtogether.security.JwtUtil;
import com.github.sveldevorls.readtogether.user.dto.UserDataResponse;
import com.github.sveldevorls.readtogether.user.entity.User;
import com.github.sveldevorls.readtogether.user.service.UserService;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    
    // hash of an invalid password (10k+ char)
    private final String dummyPasswordHash = "$argon2id$v=19$m=16384,t=2,p=1$f6Rkj64oxISvfN2Z2r27Jg$RH7XNkVCi8GQKaPyPrbuKqVRTRRQfiVa4dUow4KOScI";

    public AuthService(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponse register(RegisterRequest request) {
        User createdUser = userService.createUser(request);
        String token = jwtUtil.generateToken(createdUser.getId() ,createdUser.getUsername(), createdUser.getUserRole().name());
        return new RegisterResponse(token, UserDataResponse.fromEntity(createdUser));
    }

    public LoginResponse login(LoginRequest request) {
        String hashToCheck = userService.getPasswordHashByIdentifier(request.identifier())
                                        .orElse(dummyPasswordHash);
        if (!encoder.matches(request.password(), hashToCheck)) {
            throw new InvalidLoginCredentialsException();
        }

        User user = userService.getUserByIdentifier(request.identifier());
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getUserRole().name());
        return new LoginResponse(token, UserDataResponse.fromEntity(user));
    }   
}
