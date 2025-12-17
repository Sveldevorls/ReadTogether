package com.github.sveldevorls.readtogether.submission.entity;

import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;

public class AuthorSubmissionMapper {

    public static AuthorSubmission fromRequest(int submitterId, NewAuthorSubmissionRequest request) {
        AuthorSubmission submission = new AuthorSubmission();
        submission.setSubmitterId(submitterId);
        submission.setAuthorData(request.authorData());
        submission.setSubmitterComment(request.submitterComment());
        return submission;
    }
}
