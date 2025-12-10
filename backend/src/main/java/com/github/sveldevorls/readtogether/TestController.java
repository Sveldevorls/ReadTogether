package com.github.sveldevorls.readtogether;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/test")
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin() {
        return "Admin access point";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public String getModerator() {
        return "Moderator access point";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String getUser() {
        return "User access point";
    }

    @GetMapping("/public")
    @PreAuthorize("permitAll()")
    public String getPublic() {
        return "Public access point";
    }
}
