package com.github.sveldevorls.readtogether;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.sveldevorls.readtogether.user.dto.AdminCreationCommand;
import com.github.sveldevorls.readtogether.user.service.UserService;

@Component
public class StartupConfig {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserService userService;

    public StartupConfig(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        AdminCreationCommand command = new AdminCreationCommand(adminUsername, adminEmail, adminPassword);
        userService.createAdminIfNotExists(command);
    }
}
