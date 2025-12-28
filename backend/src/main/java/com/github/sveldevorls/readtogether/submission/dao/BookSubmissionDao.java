package com.github.sveldevorls.readtogether.submission.dao;

import java.util.List;
import java.util.Optional;

import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;

public interface BookSubmissionDao {

    // C
    // Return: id of the created submission
    int createBookSubmission(BookSubmission submission);

    void mapSubmissionBookAuthor(int submissionId, int bookId, int authorId);

    void mapSubmissionBookGenre(int submissionId, int bookId, int genreId);

    // R
    boolean isInitialized();

    Optional<Integer> getBookIdById(int id);

    List<Integer> getMappedAuthorsById(int id);

    List<Integer> getMappedGenresById(int id);

    Optional<BookSubmissionResponse> getSubmissionResponseById(int id);

    // U
    int updateReviewById(int submissionId, ReviewStatus status, int reviewerId, String reviewerComment);
}
