package com.github.sveldevorls.readtogether.submission.dto;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.github.sveldevorls.readtogether.book.entity.BookData;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewBookSubmissionRequest(

    @NotEmpty(message = "Author is required")
    @Size(min=1, message = "At least one author is required")
    @UniqueElements(message = "All authors must be unique")
    List<Integer> authors,

    @UniqueElements(message = "All genres must be unique")
    List<Integer> genres,

    @Valid
    BookData bookData,

    @Size(max = 500, message = "Comments must be at most 500 characters long") 
    String submitterComment
) {}
