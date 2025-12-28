package com.github.sveldevorls.readtogether.submission.dao;

import java.util.Optional;

import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

public interface AuthorSubmissionDao {

    // C
    // Return: id of the created submission
    int createAuthorSubmission(AuthorSubmission submission);

    // R
    Optional<AuthorSubmissionResponse> getSubmissionResponseById(int id);

    boolean isInitialized();

    /* Optional<AuthorSubmission> getSubmissionEntityById(int id); */

    Optional<Integer> getAuthorIdById(int id);

    // U
    int updateReviewById(int submissionId, ReviewStatus status, int reviewerId, String reviewerComment);
}
