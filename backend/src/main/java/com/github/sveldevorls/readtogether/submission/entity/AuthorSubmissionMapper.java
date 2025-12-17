package com.github.sveldevorls.readtogether.submission.entity;

import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;

public class AuthorSubmissionMapper {

    public static AuthorSubmission fromRequest(int submitterId, NewAuthorSubmissionRequest dto) {
        AuthorSubmission submission = new AuthorSubmission();
        submission.setSubmitterId(submitterId);
        submission.setAuthorData(dto.authorData());
        submission.setSubmitterComment(dto.submitterComment());
        return submission;
    }
}
