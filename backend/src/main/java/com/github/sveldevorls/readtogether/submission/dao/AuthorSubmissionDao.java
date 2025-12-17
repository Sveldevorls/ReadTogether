package com.github.sveldevorls.readtogether.submission.dao;

import java.util.Optional;

import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

public interface AuthorSubmissionDao {

    // C
    // Return: id of the created submission
    int createAuthorSubmission(AuthorSubmission submission);

    // R
    Optional<AuthorSubmissionResponse> getSubmissionById(int id);
}
