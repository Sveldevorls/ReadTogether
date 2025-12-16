package com.github.sveldevorls.readtogether.submission.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;
import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmissionMapper;
import com.github.sveldevorls.readtogether.author.service.AuthorService;
import com.github.sveldevorls.readtogether.submission.dao.AuthorSubmissionDAO;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionDTO;

@Service
public class AuthorSubmissionService {

    public final AuthorService authorService;

    public final AuthorSubmissionDAO submissionDao;

    public AuthorSubmissionService(AuthorSubmissionDAO submissionDao, AuthorService authorService) {
        this.submissionDao = submissionDao;
        this.authorService = authorService;
    }

    @Transactional
    public int createNewAuthorSubmission(int submitterId, AuthorSubmissionDTO dto) {

        // Insert author into [authors] as pending author, retrieve created author id
        int createdAuthorId = authorService.createAuthor(dto.authorData());

        // Create AuthorSubmission entity, set authorId as the returned id
        AuthorSubmission submission = AuthorSubmissionMapper.toEntity(submitterId, dto);
        submission.setAuthorId(createdAuthorId);

        //Insert submission into [author_submissions], retrieve created submission id for redirecting
        int createdId = submissionDao.createAuthorSubmission(submission);

        return createdId;
    }
}