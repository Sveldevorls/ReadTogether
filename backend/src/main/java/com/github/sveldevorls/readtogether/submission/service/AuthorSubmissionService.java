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
    public int createNewAuthorSubmission(int submitterId, NewAuthorSubmissionRequest dto) {

        // Insert author into [authors] as pending author, retrieve created author id
        int createdAuthorId = authorService.fromData(dto.authorData());

        // Create AuthorSubmission entity, set authorId as the returned id
        AuthorSubmission submission = AuthorSubmissionMapper.fromRequest(submitterId, dto);
        submission.setAuthorId(createdAuthorId);

        //Insert submission into [author_submissions], retrieve created submission id for redirecting
        int createdId = authorSubmissionDao.createAuthorSubmission(submission);

        return createdId;
    }

    public AuthorSubmissionResponse getSubmissionById(int id) {
        AuthorSubmissionResponse submission = 
            authorSubmissionDao.getSubmissionById(id)
                               .orElseThrow(() -> new ResourceNotFoundException());
        
        return submission;
    }
}