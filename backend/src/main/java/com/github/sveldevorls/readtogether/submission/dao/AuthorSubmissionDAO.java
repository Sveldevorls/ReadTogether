package com.github.sveldevorls.readtogether.submission.dao;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

public interface AuthorSubmissionDAO {

    // C
    // Return: id of the created submission
    int createAuthorSubmission(AuthorSubmission submission);
}
