package com.github.sveldevorls.readtogether.submission.dao;

import java.util.List;
import java.util.Optional;

import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;

public interface BookSubmissionDao {

    // C
    // Return: id of the created submission
    public int createBookSubmission(BookSubmission submission);

    public void mapSubmissionBookAuthor(int submissionId, int bookId, int authorId);

    public void mapSubmissionBookGenre(int submissionId, int bookId, int genreId);

    // R
    public Optional<Integer> getBookIdById(int id);

    public List<Integer> getMappedAuthorsById(int id);

    public List<Integer> getMappedGenresById(int id);

    public Optional<BookSubmissionResponse> getSubmissionResponseById(int id);

    // U
    int updateReviewById(int submissionId, ReviewStatus status, int reviewerId, String reviewerComment);
}
