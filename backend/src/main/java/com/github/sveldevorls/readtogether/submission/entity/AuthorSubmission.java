package com.github.sveldevorls.readtogether.submission.entity;

import java.time.Instant;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;

public class AuthorSubmission {

    private int id;
    private Instant createdAt;
    private Instant updatedAt;

    private int previousSubmissionId;
    private int authorId;
    private int submitterId;
    private String submitterComment;
    private int reviewerId;
    private String reviewerComment;
    private Instant reviewedAt;
    private ReviewStatus reviewStatus;

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

    public int getPreviousSubmissionId() {
        return previousSubmissionId;
    }

    public void setPreviousSubmissionId(int previousSubmissionId) {
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

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
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
