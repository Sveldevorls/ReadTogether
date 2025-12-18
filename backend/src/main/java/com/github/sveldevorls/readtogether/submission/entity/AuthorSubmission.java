package com.github.sveldevorls.readtogether.submission.entity;

import java.time.Instant;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;
import com.github.sveldevorls.readtogether.common.entity.ReviewStatus;

public class AuthorSubmission {

    // Meta
    private int id;
    private Instant createdAt;
    private Instant updatedAt;

    // Submission data
    private Integer previousSubmissionId;
    private int authorId;
    private int submitterId;
    private String submitterComment;
    private Integer reviewerId;
    private String reviewerComment;
    private Instant reviewedAt;
    private ReviewStatus reviewStatus;

    // Author data
    private AuthorData authorData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPreviousSubmissionId() {
        return previousSubmissionId;
    }

    public void setPreviousSubmissionId(Integer previousSubmissionId) {
        this.previousSubmissionId = previousSubmissionId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(int submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterComment() {
        return submitterComment;
    }

    public void setSubmitterComment(String submitterComment) {
        this.submitterComment = submitterComment;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerComment() {
        return reviewerComment;
    }

    public void setReviewerComment(String reviewerComment) {
        this.reviewerComment = reviewerComment;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public AuthorData getAuthorData() {
        return authorData;
    }

    public void setAuthorData(AuthorData authorData) {
        this.authorData = authorData;
    }

}
