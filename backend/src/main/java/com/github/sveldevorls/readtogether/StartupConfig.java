package com.github.sveldevorls.readtogether;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sveldevorls.readtogether.auth.dto.RegisterRequest;
import com.github.sveldevorls.readtogether.review.service.ReviewService;
import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.dto.NewBookSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.service.AuthorSubmissionService;
import com.github.sveldevorls.readtogether.submission.service.BookSubmissionService;
import com.github.sveldevorls.readtogether.user.service.UserService;

@Component
public class StartupConfig {

    private record DefaultUser(
            String username,
            String email,
            String displayName,
            String bio,
            String avatarUrl) {
    }

    private record DefaultReview(
            int userId,
            int bookId,
            int rating,
            String comment) {
    }

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final ObjectMapper objectMapper;

    private final AuthorSubmissionService authorSubmissionService;

    private final BookSubmissionService bookSubmissionService;

    private final ReviewService reviewService;

    private final UserService userService;

    public StartupConfig(
            ObjectMapper objectMapper,
            AuthorSubmissionService authorSubmissionService,
            BookSubmissionService bookSubmissionService,
            ReviewService reviewService,
            UserService userService) {

        this.objectMapper = objectMapper;
        this.authorSubmissionService = authorSubmissionService;
        this.bookSubmissionService = bookSubmissionService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            if (!userService.isInitialized()) {
                // Add the admin first
                userService.createAdmin(adminUsername, adminEmail, adminPassword);

                InputStream userStream = StartupConfig.class.getResourceAsStream("defaultUsers.json");
                DefaultUser[] users = objectMapper.readValue(
                        userStream,
                        DefaultUser[].class);
                for (DefaultUser user : users) {
                    // Not meant to be logged in, only used to host default reviews
                    String password = UUID.randomUUID().toString().replace("-", "");
                    RegisterRequest userRequest = new RegisterRequest(
                            user.username(),
                            user.email(),
                            password,
                            password);
                    userService.createUser(userRequest);
                    userService.updateDisplayName(user.username(), user.displayName());
                    userService.updateBio(user.username(), user.bio());
                    userService.updateAvatarUrl(user.username(), user.avatarUrl());
                }
                userStream.close();
            }

            if (!authorSubmissionService.isInitialized()) {
                InputStream authorStream = StartupConfig.class.getResourceAsStream("defaultAuthorSubmissions.json");
                NewAuthorSubmissionRequest[] authors = objectMapper.readValue(
                        authorStream,
                        NewAuthorSubmissionRequest[].class);
                for (int i = 0; i < authors.length; i++) {
                    authorSubmissionService.createNewAuthorSubmission(1, authors[i]);
                    authorSubmissionService.approveSubmission(i + 1, 1, null);
                }
                authorStream.close();
            }

            if (!bookSubmissionService.isInitialized()) {
                InputStream bookStream = StartupConfig.class.getResourceAsStream("defaultBookSubmissions.json");
                NewBookSubmissionRequest[] books = objectMapper.readValue(
                        bookStream,
                        NewBookSubmissionRequest[].class);
                for (int i = 0; i < books.length; i++) {
                    bookSubmissionService.createNewBookSubmission(1, books[i]);
                    bookSubmissionService.approveSubmission(i + 1, 1, null);
                }
                bookStream.close();
            }

            if (!reviewService.isInitialized()) {
                InputStream reviewStream = StartupConfig.class.getResourceAsStream("defaultReviews.json");
                DefaultReview[] reviews = objectMapper.readValue(
                        reviewStream,
                        DefaultReview[].class);
                for (DefaultReview review : reviews) {
                    reviewService.createReview(
                            review.userId(),
                            review.bookId(),
                            review.rating(),
                            review.comment());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
