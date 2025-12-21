package com.github.sveldevorls.readtogether.submission.mapper;

import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

public class AuthorSubmissionMapper {

    public static AuthorSubmission fromRequest(NewAuthorSubmissionRequest request) {
        AuthorSubmission submission = new AuthorSubmission();
        submission.setAuthorData(request.authorData());
        submission.setSubmitterComment(request.submitterComment());
        return submission;
    }
}
