package com.github.sveldevorls.readtogether.home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sveldevorls.readtogether.common.response.SuccessResponse;

@RestController
@RequestMapping(path = "/api/homepage", produces = "application/json")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getHomepageData() {
        HomepageResponse response = homeService.getHomepageData();

        return new ResponseEntity<>(
                new SuccessResponse(
                        HttpStatus.OK,
                        "Logged in successfully",
                        response),
                HttpStatus.OK);
    }
}
