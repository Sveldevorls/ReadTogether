package com.github.sveldevorls.readtogether.submission.mapper;

import com.github.sveldevorls.readtogether.submission.dto.NewBookSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;

public class BookSubmissionMapper {

    public static BookSubmission fromRequest(NewBookSubmissionRequest request) {
        BookSubmission submission = new BookSubmission();
        submission.setBookData(request.bookData());
        submission.setSubmitterComment(request.submitterComment());
        return submission;
    }
}
