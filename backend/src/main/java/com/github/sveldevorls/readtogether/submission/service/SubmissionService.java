package com.github.sveldevorls.readtogether.submission.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.submission.entity.AuthorSubmission;
import com.github.sveldevorls.readtogether.submission.AuthorSubmissionMapper;
import com.github.sveldevorls.readtogether.submission.dao.SubmissionDAO;
import com.github.sveldevorls.readtogether.submission.dto.AuthorSubmissionDTO;

@Service
public class SubmissionService {
    
    public final SubmissionDAO submissionDao;
    
    public SubmissionService(SubmissionDAO submissionDao) {
        this.submissionDao = submissionDao;
    }

    // Todo: insert author into authors table as pending
    public int createNewAuthorSubmission(int submitterId, AuthorSubmissionDTO dto) {
        AuthorSubmission submission = AuthorSubmissionMapper.toEntity(submitterId, dto);
        int createdId = submissionDao.createAuthorSubmission(submission);

        return createdId;
    }
}