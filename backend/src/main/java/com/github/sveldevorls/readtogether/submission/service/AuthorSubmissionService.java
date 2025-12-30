package com.github.sveldevorls.readtogether.submission.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;
import com.github.sveldevorls.readtogether.submission.mapper.AuthorSubmissionMapper;
import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;
import com.github.sveldevorls.readtogether.common.exception.ResourceNotFoundException;
import com.github.sveldevorls.readtogether.submission.dao.AuthorSubmissionDao;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionResponse;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionSummary;
import com.github.sveldevorls.readtogether.submission.dto.NewAuthorSubmissionRequest;
import com.github.sveldevorls.readtogether.submission.dto.SubmissionListingResponse;

@Service
public class AuthorSubmissionService {

    public final AuthorService authorService;

    public final AuthorSubmissionDao authorSubmissionDao;

    public AuthorSubmissionService(AuthorSubmissionDao authorSubmissionDao, AuthorService authorService) {
        this.authorSubmissionDao = authorSubmissionDao;
        this.authorService = authorService;
    }

    public boolean isInitialized() {
        return authorSubmissionDao.isInitialized();
    }

    @Transactional
    public int createNewAuthorSubmission(int submitterId, NewAuthorSubmissionRequest request) {

        // Insert author into [authors] as pending author, retrieve created author id
        int createdAuthorId = authorService.createFromAuthorData(request.authorData());

        // Create AuthorSubmission entity, set authorId as the returned id
        AuthorSubmission submission = AuthorSubmissionMapper.fromRequest(request);
        submission.setSubmitterId(submitterId);
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
    public AuthorSubmissionResponse approveSubmission(
            int submissionId,
            int reviewerId,
            String reviewerComment) {

        // Fetch original author id for the submission
        int authorId = authorSubmissionDao
                .getAuthorIdById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        // Update submission review status
        int submissionRows = authorSubmissionDao.updateReviewById(
                submissionId,
                ReviewStatus.approved,
                reviewerId,
                reviewerComment);

        if (submissionRows == 0)
            throw new ResourceNotFoundException("Submission was not found, try refreshing the page");
        if (submissionRows > 1)
            throw new DataIntegrityViolationException("Multiple submissions were found when only one was expected");

        // Mark the corresponding author as approved
        authorService.approveAuthor(authorId);

        // Fetch the newly updated submission response
        AuthorSubmissionResponse response = authorSubmissionDao
                .getSubmissionResponseById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }

    @Transactional
    public AuthorSubmissionResponse rejectSubmission(
            int submissionId,
            int reviewerId,
            String reviewerComment) {

        // Fetch original author id for the submission
        int authorId = authorSubmissionDao
                .getAuthorIdById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        // Update submission review status
        int rows = authorSubmissionDao.updateReviewById(
                submissionId,
                ReviewStatus.rejected,
                reviewerId,
                reviewerComment);

        if (rows == 0)
            throw new ResourceNotFoundException("Submission was not found, try refreshing the page");
        if (rows > 1)
            throw new DataIntegrityViolationException("Multiple submissions were found when only one was expected");

        // Remove the rejected author
        authorService.rejectAuthor(authorId);

        // Fetch the newly updated submission response
        AuthorSubmissionResponse response = authorSubmissionDao
                .getSubmissionResponseById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException());

        return response;
    }

    public SubmissionListingResponse<AuthorSubmissionSummary> getSubmissionListing(
            Integer limit,
            Integer page,
            String status) {

        Integer limitValue = limit != null ? limit : 10;
        Integer pageValue = page != null ? page : 0;
        String statusValue = status != null ? status : "pending";
        SubmissionListingResponse<AuthorSubmissionSummary> result = authorSubmissionDao.getSubmissionListing(
                limitValue,
                pageValue,
                statusValue);
        return result;
    }
}