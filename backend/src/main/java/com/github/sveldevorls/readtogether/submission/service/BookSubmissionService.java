package com.github.sveldevorls.readtogether.submission.service;

import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.NewBookSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;
import com.github.sveldevorls.readtogether.submission.mapper.BookSubmissionMapper;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sveldevorls.readtogether.book.service.BookService;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.submission.dao.BookSubmissionDao;

@Service
public class BookSubmissionService {

    private final BookService bookService;

    private final BookSubmissionDao bookSubmissionDao;

    public BookSubmissionService(BookService bookService, BookSubmissionDao bookSubmissionDao) {
        this.bookService = bookService;
        this.bookSubmissionDao = bookSubmissionDao;
    }

    @Transactional
    public int createNewBookSubmission(int submitterId, NewBookSubmissionRequest request) {

        // Insert book into [books] as pending book, retrieve created book id
        int createdBookId = bookService.createFromBookData(request.bookData());

        // Create BookSubmission entity
        BookSubmission submission = BookSubmissionMapper.fromRequest(request);
        submission.setSubmitterId(submitterId);
        submission.setBookId(createdBookId);
        
        // Insert submission into [book_submissions], retrieve created submission id
        // for redirecting
        int createdSubmissionId = bookSubmissionDao.createBookSubmission(submission);

        // Map submitted book <-> author
        for (int authorId : request.authors()) {
            bookSubmissionDao.mapSubmissionBookAuthor(createdSubmissionId, createdBookId, authorId);
        }

        // Map submitted book <-> genre
        for (int genreId : request.genres()) {
            bookSubmissionDao.mapSubmissionBookGenre(createdSubmissionId, createdBookId, genreId);
        }

        return createdSubmissionId;
    }

    public BookSubmissionResponse getSubmissionResponseById(int id) {
        BookSubmissionResponse submission = bookSubmissionDao
                .getSubmissionResponseById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        return submission;
    }

    @Transactional
    public BookSubmissionResponse approveSubmission(
            int submissionId,
            int reviewerId,
            String reviewerComment) {

        // Fetch original book id for the submission
        int bookId = bookSubmissionDao
                .getBookIdById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        // Update submission review status
        int submissionRows = bookSubmissionDao.updateReviewById(
                submissionId,
                ReviewStatus.approved,
                reviewerId,
                reviewerComment);

        if (submissionRows == 0)
            throw new ResourceNotFoundException("Submission was not found, try refreshing the page");
        if (submissionRows > 1)
            throw new DataIntegrityViolationException("Multiple submissions were found when only one was expected");

        // Mark the corresponding book as approved
        bookService.approveBook(bookId);

        // Add corresponding book <-> author map
        List<Integer> authorIds = bookSubmissionDao.getMappedAuthorsById(submissionId);
        for (int authorId: authorIds) {
            bookService.mapBookAuthor(bookId, authorId);
        }
        
        // Add corresponding book <-> genre map
        List<Integer> genreIds = bookSubmissionDao.getMappedGenresById(submissionId);
        for (int genreId: genreIds) {
            bookService.mapBookGenre(bookId, genreId);
        }

        // Fetch the newly updated submission response
        BookSubmissionResponse response = bookSubmissionDao
                .getSubmissionResponseById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }

    @Transactional
    public BookSubmissionResponse rejectSubmission(
            int submissionId,
            int reviewerId,
            String reviewerComment) {

        // Fetch original author id for the submission
        int bookId = bookSubmissionDao
                .getBookIdById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        // Update submission review status
        int rows = bookSubmissionDao.updateReviewById(
                submissionId,
                ReviewStatus.rejected,
                reviewerId,
                reviewerComment);

        if (rows == 0)
            throw new ResourceNotFoundException("Submission was not found, try refreshing the page");
        if (rows > 1)
            throw new DataIntegrityViolationException("Multiple submissions were found when only one was expected");

        // Remove the rejected author
        bookService.rejectBook(bookId);

        // Fetch the newly updated submission response
        BookSubmissionResponse response = bookSubmissionDao
                .getSubmissionResponseById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }
}
