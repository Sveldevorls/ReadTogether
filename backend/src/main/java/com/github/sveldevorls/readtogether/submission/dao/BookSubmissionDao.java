package com.github.sveldevorls.readtogether.submission.dao;

import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;

public interface BookSubmissionDao {

    // C
    // Return: id of the created submission
    public int createBookSubmission(BookSubmission submission);

    public void mapSubmissionBookAuthor(int submissionId, int bookId, int authorId);

    public void mapSubmissionBookGenre(int submissionId, int bookId, int genreId);
}
