package com.github.sveldevorls.readtogether.submission.entity;

import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionDTO;

public class AuthorSubmissionMapper {

    public static AuthorSubmission toEntity(int submitterId, AuthorSubmissionDTO dto) {
        AuthorSubmission submission = new AuthorSubmission();
        submission.setSubmitterId(submitterId);
        submission.setAuthorData(dto.authorData());
        submission.setSubmitterComment(dto.submitterComment());
        return submission;
    }
}
