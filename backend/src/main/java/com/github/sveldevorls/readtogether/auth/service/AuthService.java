package com.github.sveldevorls.readtogether.auth.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.LoginRequestDTO;
import com.github.sveldevorls.readtogether.auth.dto.LoginResponseDTO;
import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.auth.dto.RegisterResponseDTO;
import com.github.sveldevorls.readtogether.auth.exception.InvalidLoginCredentialsException;
import com.github.sveldevorls.readtogether.security.JwtUtil;
import com.github.sveldevorls.readtogether.user.dto.UserDataDTO;
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

    public RegisterResponseDTO register(RegisterRequestDTO dto) {
        User createdUser = userService.createUser(dto);
        String token = jwtUtil.generateToken(createdUser.getId() ,createdUser.getUsername(), createdUser.getUserRole().name());
        return new RegisterResponseDTO(token, UserDataDTO.fromEntity(createdUser));
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        String hashToCheck = userService.getPasswordHashByIdentifier(dto.identifier())
                                        .orElse(dummyPasswordHash);
        if (!encoder.matches(dto.password(), hashToCheck)) {
            throw new InvalidLoginCredentialsException();
        }

        User user = userService.getUserByIdentifier(dto.identifier());
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getUserRole().name());
        return new LoginResponseDTO(token, UserDataDTO.fromEntity(user));
    }   
}
