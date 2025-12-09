package com.github.sveldevorls.readtogether.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponseDTO;
import com.github.sveldevorls.readtogether.user.dto.UserDTO;
import com.github.sveldevorls.readtogether.user.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/api/users/", produces = "application/json")
public class UserPageController {

    private final UserService userService;

    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<SuccessResponseDTO> getUserData(@PathVariable String username) {
        UserDTO response = userService.getUserPageData(username);
        return new ResponseEntity<>(
                new SuccessResponseDTO(HttpStatus.OK, response),
                HttpStatus.OK);
    }

}
