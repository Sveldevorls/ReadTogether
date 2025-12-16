package com.github.sveldevorls.readtogether.submission;

import java.time.LocalDate;

import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionDTO;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;

public class AuthorSubmissionMapper {

    public static AuthorSubmission toEntity(int submitterId, AuthorSubmissionDTO dto) {
        AuthorSubmission submission = new AuthorSubmission();
        submission.setSubmitterId(submitterId);
        submission.setAuthorName(dto.authorName());
        submission.setDateOfBirth(LocalDate.parse(dto.dateOfBirth()));
        submission.setDateOfDeath(LocalDate.parse(dto.dateOfDeath()));
        submission.setAuthorImageUrl(dto.authorImageUrl());
        submission.setBiography(dto.biography());
        submission.setSubmitterComment(dto.submitterComment());
        return submission;
    }
}
