package com.github.sveldevorls.readtogether.submission.service;

import com.github.sveldevorls.readtogether.submission.dto.BookSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.NewBookSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.entity.BookSubmission;
import com.github.sveldevorls.readtogether.submission.mapper.BookSubmissionMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sveldevorls.readtogether.book.service.BookService;
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
}
