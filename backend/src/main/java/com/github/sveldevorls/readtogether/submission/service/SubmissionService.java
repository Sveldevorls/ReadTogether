package com.github.sveldevorls.readtogether.submission.service;

import org.springframework.stereotype.Service;

import com.github.sveldevorls.readtogether.submission.dao.SubmissionDAO;

@Service
public class SubmissionService {
    
    public final SubmissionDAO submissionDao;
    
    public SubmissionService(SubmissionDAO submissionDao) {
        this.submissionDao = submissionDao;
    }
}