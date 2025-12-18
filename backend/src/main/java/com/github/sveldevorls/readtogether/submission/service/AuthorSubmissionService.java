package com.github.sveldevorls.readtogether.submission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmissionMapper;
import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.submission.dao.AuthorSubmissionDao;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;

@Service
public class AuthorSubmissionService {

    public final AuthorService authorService;

    public final AuthorSubmissionDao authorSubmissionDao;

    public AuthorSubmissionService(AuthorSubmissionDao authorSubmissionDao, AuthorService authorService) {
        this.authorSubmissionDao = authorSubmissionDao;
        this.authorService = authorService;
    }

    @Transactional
    public int createNewAuthorSubmission(int submitterId, NewAuthorSubmissionRequest request) {

        // Insert author into [authors] as pending author, retrieve created author id
        int createdAuthorId = authorService.createFromAuthorData(request.authorData());

        // Create AuthorSubmission entity, set authorId as the returned id
        AuthorSubmission submission = AuthorSubmissionMapper.fromRequest(submitterId, request);
        submission.setAuthorId(createdAuthorId);

        // Insert submission into [author_submissions], retrieve created submission id
        // for redirecting
        int createdId = authorSubmissionDao.createAuthorSubmission(submission);

        return createdId;
    }

    public AuthorSubmissionResponse getSubmissionResponseById(int id) {
        AuthorSubmissionResponse submission = authorSubmissionDao
                .getSubmissionResponseById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        return submission;
    }

    @Transactional
    public AuthorSubmissionResponse approveSubmission(int id, String reviewerComment) {

        // Fetch original author id for the submission
        int authorId = authorSubmissionDao
                .getAuthorIdById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        // Update submission review status
        authorSubmissionDao.updateReviewStatusById(id, "approved");
        authorSubmissionDao.updateReviewerCommentById(id, reviewerComment);

        // Mark the corresponding author as approved
        authorService.approveAuthor(authorId);

        // Fetch the newly updated submission response
        AuthorSubmissionResponse response = authorSubmissionDao
                .getSubmissionResponseById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }

    @Transactional
    public AuthorSubmissionResponse rejectSubmission(int id, String reviewerComment) {

        // Fetch original author id for the submission
        int authorId = authorSubmissionDao
                .getAuthorIdById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        // Update submission review status
        authorSubmissionDao.updateReviewStatusById(id, "approved");
        authorSubmissionDao.updateReviewerCommentById(id, reviewerComment);

        // Remove the rejected author
        authorService.deleteAuthor(authorId);

        // Fetch the newly updated submission response
        AuthorSubmissionResponse response = authorSubmissionDao
                .getSubmissionResponseById(id)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }
}