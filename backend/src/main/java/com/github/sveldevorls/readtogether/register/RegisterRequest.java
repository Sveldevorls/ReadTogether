package com.github.sveldevorls.readtogether.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username can not be blank")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email format must be valid")
    private String email;
    
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;

    @NotBlank(message = "Password confirmation can not be blank")
    private String passwordConfirm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
