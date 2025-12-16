package com.github.sveldevorls.readtogether.submission.dto;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public record AuthorSubmissionDTO(

    @Valid
    AuthorData authorData,

    @Size(max = 500, message = "Comments must be at most 500 characters long") 
    String submitterComment
) {}
