package com.github.sveldevorls.readtogether.submission.dto;

import org.hibernate.validator.constraints.URL;

import com.github.sveldevorls.readtogether.submission.validation.ValidDobAndDod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Todo: verify dob-dod non-future?
@ValidDobAndDod
public record AuthorSubmissionDTO(

    @NotBlank(message = "Author name is required")
    @Size(max = 255, message= "Author name must be at most 255 characters long")
    String authorName,

    String dateOfBirth,

    String dateOfDeath,

    @URL(message = "Link to the author's image must be a valid URL")
    String authorImageUrl,

    @Size(max = 500, message= "Author biography must be at most 500 characters long")
    String biography,

    @Size(max = 500, message= "Comments must be at most 500 characters long")
    String submitterComment
) {}
