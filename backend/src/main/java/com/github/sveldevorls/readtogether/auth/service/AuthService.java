package com.github.sveldevorls.readtogether.auth.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.auth.dto.LoginRequestDTO;
import com.github.sveldevorls.readtogether.auth.dto.RegisterRequestDTO;
import com.github.sveldevorls.readtogether.auth.exception.InvalidLoginCredentialsException;
import com.github.sveldevorls.readtogether.user.service.UserService;

@Service
public class AuthService {

    private final UserService userService;
    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    
    // hash of an invalid password (10k+ char)
    private final String dummyPasswordHash = "$argon2id$v=19$m=16384,t=2,p=1$f6Rkj64oxISvfN2Z2r27Jg$RH7XNkVCi8GQKaPyPrbuKqVRTRRQfiVa4dUow4KOScI";

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(RegisterRequestDTO dto) {
        userService.createUser(dto);
    }

    public void login(LoginRequestDTO dto) {
        String currentUserHash = userService.getPasswordHashByIdentifier(dto.identifier());
        String hashToCheck = (currentUserHash != null) ? currentUserHash : dummyPasswordHash;
        if (!encoder.matches(dto.password(), hashToCheck)) {
            throw new InvalidLoginCredentialsException();
        }
    }   
}
